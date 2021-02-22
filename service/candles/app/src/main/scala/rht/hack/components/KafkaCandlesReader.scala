package rht.hack.components

import akka.actor.ActorRef
import cats.effect.Resource
import monix.eval.Task
import monix.execution.Scheduler
import rht.common.domain.candles.Candle
import rht.kafka.consumer.{JsonMessageReader, KafkaConsumer, MessageReader}
import tethys.JsonReader
import tofu.logging.Logs

object KafkaCandlesReader {

  case class ConsumerEntry(eventId: String, candle: Candle)
  object ConsumerEntry {
    implicit val tethysReader: JsonReader[ConsumerEntry] = tethys.derivation.semiauto.jsonReader[ConsumerEntry]
    implicit val messageReader: MessageReader[Task, ConsumerEntry] = JsonMessageReader[Task, ConsumerEntry]
  }

  def mkBuilder(configComponent: ConfigComponent)(implicit s: Scheduler, logs: Logs[Task, Task]): ActorRef => Resource[Task, Unit] = { ref =>
    for {
      logging <- Resource.liftF(logs.byName("ConsumerComponent"))
      _ <- KafkaConsumer[Task, ConsumerEntry](configComponent.kafkaConsumer) { entry =>
        Task.delay(ref.tell(entry.candle, ActorRef.noSender)).onErrorHandleWith { error =>
          logging.errorCause("Error while receiving message", error)
        }
      }
    } yield ()

  }

}
