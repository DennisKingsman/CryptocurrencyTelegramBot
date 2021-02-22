package rht.common.domain.candles

import io.estatico.newtype.macros.newtype
import pureconfig.ConfigReader
import tethys.{JsonReader, JsonWriter}

object common {

  @newtype
  case class Figi(value: String)
  object Figi {
    implicit val reader: JsonReader[Figi] = deriving
    implicit val writer: JsonWriter[Figi] = deriving
    implicit val configReader: ConfigReader[Figi] = deriving
  }

}
