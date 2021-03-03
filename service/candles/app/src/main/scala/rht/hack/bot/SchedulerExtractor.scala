package rht.hack.bot

import monix.execution.Scheduler
import monix.execution.schedulers.SchedulerService
import rht.hack.dao.RedisCandleDao
import io.circe._

import java.util.concurrent.TimeUnit
import scala.collection.mutable


// Do not use! Not working!
// A scheduler for a bot.

object SchedulerExtractor {

  def start(figi: String, chatId: Long, botToken: String): Unit = {
    val scheduler =
      Scheduler.singleThread(name=scala.util.Random.nextString(10))

    // First execution in 1 seconds, then every 5 seconds
    scheduler.scheduleAtFixedRate(
      1, 5, TimeUnit.SECONDS,
      new Runnable {
        def run(): Unit = {

          val msg = RedisCandleDao.readCandle(figi)
          val url = s"https://api.telegram.org/bot{$botToken}/sendMessage?chat_id={$chatId}&text={$msg}"

        }
      })

    ChatCache.cache(chatId) = scheduler
  }

  def shutdown(figi: String, chatId: Long): Unit = {
    val scheduler = ChatCache.cache(chatId)
    scheduler.shutdown()
    ChatCache.cache -= chatId
  }

  def main(args: Array[String]): Unit = {
    CryptoBot.run()
  }

}


object ChatCache {
  val cache = mutable.HashMap[Long, SchedulerService]().empty
}
