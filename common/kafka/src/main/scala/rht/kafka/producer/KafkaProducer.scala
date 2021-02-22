package rht.kafka.producer

import java.nio.charset.StandardCharsets

import cats.effect.{ConcurrentEffect, ContextShift, Resource}
import cats.effect.concurrent.Deferred
import cats.syntax.applicativeError._
import cats.syntax.flatMap._
import cats.syntax.functor._
import fs2.kafka._
import fs2.kafka.{KafkaProducer => Fs2Producer}
import rht.kafka.config.KafkaProducerConfig
import tofu.logging.{Logging, Logs}

class KafkaProducer[F[_]: ConcurrentEffect: ContextShift, M] private (
    config: KafkaProducerConfig,
    deferredP: Deferred[F, Fs2Producer[F, String, M]]
)(
    implicit
    MW: MessageWriter[F, M],
    logging: Logging[F]
) {

  private implicit val serializer: RecordSerializer[F, M] = RecordSerializer.lift(
    Serializer.lift(message => ConcurrentEffect[F].defer(MW.write(message).map(_.getBytes(StandardCharsets.UTF_8))))
  )

  private[producer] def start(): Resource[F, Unit] = {
    def mkSettings: F[ProducerSettings[F, String, M]] =
      ConcurrentEffect[F].delay(ProducerSettings[F, String, M].withProperties(config.props))

    def mkStream(settings: ProducerSettings[F, String, M]) =
      producerResource[F].using(settings)

    for {
      _        <- Resource.liftF(logging.info(s"Starting producer. Topic: ${config.topic}"))
      settings <- Resource.liftF(mkSettings)
      producer <- mkStream(settings)
      _        <- Resource.liftF(deferredP.complete(producer))
      _        <- Resource.liftF(logging.info(s"Started producer. Topic: ${config.topic}"))
    } yield ()
  }

  def send(key: String, message: M): F[Unit] =
    (for {
      producer <- deferredP.get
      _        <- logging.info(s"Sending message. Key: ${key}. Topic: ${config.topic}")
      record = ProducerRecords.one(ProducerRecord(config.topic, key, message))
      _ <- producer.produce(record)
    } yield ()).onError {
      case e => logging.errorCause(s"Error while sending message. Key: ${key}. Topic: ${config.topic}", e)
    }
}

object KafkaProducer {

  def apply[F[_]: ConcurrentEffect: ContextShift, M: MessageWriter[F, *]](
      config: KafkaProducerConfig
  )(implicit L: Logs[F, F]): Resource[F, KafkaProducer[F, M]] =
    for {
      logging  <- Resource.liftF(L.forService[KafkaProducer[F, M]])
      deferred <- Resource.liftF(Deferred.uncancelable[F, Fs2Producer[F, String, M]])
      producer <- {
        implicit val l: Logging[F] = logging
        Resource.liftF(ConcurrentEffect[F].delay(new KafkaProducer[F, M](config, deferred)))
      }
      _ <- producer.start()
    } yield producer

}
