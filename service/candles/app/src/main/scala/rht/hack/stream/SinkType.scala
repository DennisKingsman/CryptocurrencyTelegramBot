package rht.hack.stream

import akka.Done
import akka.stream.scaladsl.Sink
import rht.common.domain.candles.Candle
import rht.hack.dao.RedisCandleDao

import scala.concurrent.Future

object SinkType {

  //-----------//
  // choose one of these as a sink.

  val CONSOLE = toConsole
  val REDIS = toRedis

  //-----------//
  //-----------//



  private def toConsole: Sink[Any, Future[Done]] = {
    Sink.foreach(println)
  }

  private def toRedis: Sink[Any, Future[Done]] = {
    Sink.foreach(castInsertAndLogError)
  }

  private def castInsertAndLogError: Any => Unit = { item =>
    val candle = item.asInstanceOf[Candle]
    insertAndThenLogError(candle)
  }

  private def insertAndThenLogError: Candle => Unit = {
    RedisCandleDao.insertCandle _ andThen logInsertion
  }

  private def logInsertion(insertionCode: Boolean): Unit = {
    if (!insertionCode)
      println("!!! THE CANDLE WAS NOT INSERTED IN REDIS!!!")
  }

}
