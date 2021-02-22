package rht.kafka.producer

import cats.effect.Sync
import tethys._
import tethys.jackson._

trait MessageWriter[F[_], M] {

  def write(message: M): F[String]

}

trait JsonMessageWriter[F[_], M] extends MessageWriter[F, M]
object JsonMessageWriter {
  def apply[F[_]: Sync, M: JsonWriter]: JsonMessageWriter[F, M] =
    (message: M) => Sync[F].delay(message.asJson)
}
