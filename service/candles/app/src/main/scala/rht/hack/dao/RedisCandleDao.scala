package rht.hack.dao

import com.redis.RedisClient
import rht.common.domain.candles.Candle
import rht.common.domain.candles.common.Figi

object RedisCandleDao {

  private val redisClient = new RedisClient("localhost", 6379)

  def insertCandle(candle: Candle): Boolean = {
    val figi = candle.figi.toString
    val candleMap = Map(
      "interval" -> candle.interval.toString,
      "low" -> candle.details.low.toString,
      "high" -> candle.details.high.toString,
      "open" -> candle.details.open.toString,
      "close" -> candle.details.close.toString,
      "openTime" -> candle.details.openTime.toString,
    )

    redisClient.hmset(figi, candleMap)
  }

}
