package rht.common.domain.candles

import java.time.Instant

import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import rht.common.domain.JsonProtocol._
import rht.common.domain.candles.common.Figi

import scala.concurrent.duration.FiniteDuration

@derive(tethysWriter, tethysReader)
case class Candle(interval: FiniteDuration, figi: common.Figi, details: CandleDetails)

@derive(tethysWriter, tethysReader)
case class CandleDetails(low: BigDecimal, high: BigDecimal, open: BigDecimal, close: BigDecimal, openTime: Instant)

object Figis {

  val BTC = Figi("BTC")
  val ETH = Figi("ETH")
  val BNB = Figi("BNB")
  val DOGE = Figi("DOGE")
  val DOT = Figi("DOT")
  val ADA = Figi("ADA")

}
