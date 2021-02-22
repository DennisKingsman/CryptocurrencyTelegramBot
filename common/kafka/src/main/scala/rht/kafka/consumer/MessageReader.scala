package rht.kafka.consumer

import cats.effect.Sync
import cats.syntax.flatMap._
import tethys._
import tethys.jackson._

trait MessageReader[F[_], M] {

  def read(input: String): F[M]

}

trait JsonMessageReader[F[_], M] extends MessageReader[F, M]
object JsonMessageReader {
  def apply[F[_]: Sync, M: JsonReader]: JsonMessageReader[F, M] =
    (input: String) => Sync[F].delay(input.jsonAs[M]).flatMap(Sync[F].fromEither)
}
