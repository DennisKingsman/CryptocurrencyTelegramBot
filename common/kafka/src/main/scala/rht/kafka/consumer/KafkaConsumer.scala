package rht.kafka.consumer

import java.nio.charset.StandardCharsets

import scala.concurrent.duration._
import cats.effect.concurrent.Deferred
import cats.effect.{ConcurrentEffect, ContextShift, Fiber, Resource, Sync, Timer}
import cats.syntax.applicativeError._
import cats.syntax.flatMap._
import cats.syntax.functor._
import fs2.kafka._
import rht.kafka.config.KafkaConsumerConfig
import tofu.logging.{LoggedValue, Logging, Logs}

class KafkaConsumer[F[_]: ConcurrentEffect: Timer: ContextShift, M](
    config: KafkaConsumerConfig,
    interrupt: KafkaConsumer.Interrupt[F]
)(
    handler: M => F[Unit]
)(
    implicit MR: MessageReader[F, M],
    logging: Logging[F]
) {

  private implicit val deseriailzer: RecordDeserializer[F, M] =
    RecordDeserializer.lift {
      Deserializer.lift(bytes => Sync[F].delay(new String(bytes, StandardCharsets.UTF_8)).flatMap(MR.read))
    }

  def start(): F[Fiber[F, Unit]] = {
    val mkConsumerSettings: F[ConsumerSettings[F, String, M]] =
      Sync[F].delay(ConsumerSettings[F, String, M].withProperties(config.props))

    def mkStream(consumerSettings: ConsumerSettings[F, String, M]) =
      Sync[F].delay {
        consumerStream(consumerSettings)
          .evalTap(_.subscribeTo(config.topic))
          .flatMap(_.stream)
          .evalMap { committable =>
            for {
              _ <- logging.info(s"Received message. Topic: ${config.topic}. Key: ${committable.record.key}")
              _ <- handler(committable.record.value).onError {
                case e => logging.errorCause("Error while processing message", e)
              }
            } yield committable.offset
          }
          .through(commitBatchWithin(500, 10.seconds))
      }

    val body = for {
      _        <- logging.info(s"Starting kafka consumer. Topic: ${config.topic}")
      settings <- mkConsumerSettings
      stream   <- mkStream(settings)
      interrupter = stream.interruptWhen[F](interrupt)
      _ <- stream.concurrently(interrupter).compile.drain
      _ <- logging.info(s"Started kafka consumer. Topic: ${config.topic}")
      f <- ConcurrentEffect[F].start(Sync[F].delay(()))
    } yield f

    body.onError {
      case e => logging.errorCause("Error", e)
    }

  }

}

object KafkaConsumer {
  type Interrupt[F[_]] = Deferred[F, Either[Throwable, Unit]]

  def apply[F[_]: ConcurrentEffect: Timer: ContextShift, M: MessageReader[F, *]](
      config: KafkaConsumerConfig
  )(handler: M => F[Unit])(implicit logs: Logs[F, F]): Resource[F, Unit] =
    for {
      logging  <- Resource.liftF(logs.forService[KafkaConsumer[F, M]])
      deferred <- Resource.liftF(Deferred.uncancelable[F, Either[Throwable, Unit]])
      consumer <- {
        implicit val l: Logging[F] = logging
        Resource.liftF(ConcurrentEffect[F].delay(new KafkaConsumer(config, deferred)(handler)))
      }
      _ <- Resource.make(consumer.start()) { fiber =>
        deferred.complete(Right(())) >> fiber.cancel
      }
    } yield ()

}
