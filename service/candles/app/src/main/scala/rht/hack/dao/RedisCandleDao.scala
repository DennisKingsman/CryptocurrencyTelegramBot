package rht.hack.dao

import com.redis.RedisClient
import com.typesafe.config.ConfigFactory
import rht.common.domain.candles.Candle
import rht.common.domain.candles.common.Figi

object RedisCandleDao {

  private val config = ConfigFactory.load("application.conf")

  private val redisConfig = config.getConfig("app.redis")

  private val redisClient = new RedisClient(
    redisConfig.getString("address"),
    redisConfig.getString("port").toInt
  )

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

  def readCandle(figi: String): String = {
    val values = List("interval","low","high","open","close","openTime")

    val data = redisClient.hmget(figi, values).getOrElse("Error at extracting from Redis!")

    data.toString
  }

}
