package rht.common.domain

import java.time.Instant
import java.time.format.DateTimeFormatter

import tethys.{JsonReader, JsonWriter}

import scala.concurrent.duration._

object JsonProtocol {

  implicit val instantJsonWriter: JsonWriter[Instant] = JsonWriter.stringWriter.contramap(DateTimeFormatter.ISO_INSTANT.format)
  implicit val instantJsonReader: JsonReader[Instant] = JsonReader.stringReader.map(Instant.parse)

  implicit val finiteDurationJsonWriter: JsonWriter[FiniteDuration] = JsonWriter.longWriter.contramap(_.toMillis)
  implicit val finiteDurationJsonReader: JsonReader[FiniteDuration] = JsonReader.longReader.map(_.millis)

}
