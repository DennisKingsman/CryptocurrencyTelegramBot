package rht.hack.dao

import org.scalatest.flatspec.AnyFlatSpec
import rht.common.domain.candles.{Candle, CandleDetails, Figis}

import java.time.Instant
import scala.concurrent.duration.{FiniteDuration, SECONDS}

class RedisCandleDaoTest extends AnyFlatSpec {

  "RedisCandleDao" should "insert test candles into Redis" in {
    val candle = Candle(
      FiniteDuration(3, SECONDS),
      Figis.BTC,
      CandleDetails(25123.97, 45864.23, 31653.55, 42777.15, Instant.now())
    )

    val insertion = RedisCandleDao.insertCandle(candle)

    assert(insertion)
  }

  it should "insert different figis" in {
    val candles = List(
      Candle(
        FiniteDuration(3, SECONDS),
        Figis.BTC,
        CandleDetails(25123.97, 45864.23, 31653.55, 42777.15, Instant.now())
      ),
      Candle(
        FiniteDuration(3, SECONDS),
        Figis.ETH,
        CandleDetails(1.25, 2.25, 5.78, 9.34, Instant.now())
      ),
      Candle(
        FiniteDuration(3, SECONDS),
        Figis.DOGE,
        CandleDetails(0.53, 0.72, 0.65, 0.68, Instant.now())
      ),
    )

    val insertions = candles.map(candle => RedisCandleDao.insertCandle(candle))

    assert(insertions.reduce(_ && _))
  }

}
