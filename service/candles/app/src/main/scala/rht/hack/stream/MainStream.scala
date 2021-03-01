package rht.hack.stream

import akka.Done
import akka.actor.{ActorRef, ActorSystem}
import akka.stream.{CompletionStrategy, OverflowStrategy}
import akka.stream.scaladsl._
import rht.common.domain.candles.{Candle, CandleDetails}
import rht.common.domain.candles.Figis
import rht.hack.Main.SourceActor

import java.time.Instant
import scala.concurrent.duration.{Duration, FiniteDuration, SECONDS}

object MainStream {

  def getSourceActorStream: SourceActor = {
    implicit val actorSystem: ActorSystem = ActorSystem()
    //implicit val materializer: Materializer = Materializer()

    val source: Source[Any, ActorRef] = Source.actorRef(
      completionMatcher = {
        case Done =>
          println("Done was passed. Terminating the system.")
          actorSystem.terminate()
          // complete stream immediately if we send it Done
          CompletionStrategy.immediately

      },
      // never fail the stream because of a message
      failureMatcher = PartialFunction.empty,
      bufferSize = 2000,
      overflowStrategy = OverflowStrategy.dropHead)

    val streamActor: ActorRef = source.to(Sink.foreach(println)).run()

    streamActor
  }

  def main(args: Array[String]): Unit = {

    implicit val actorSystem: ActorSystem = ActorSystem()
    //implicit val materializer: Materializer = Materializer()

    val source: Source[Any, ActorRef] = Source.actorRef(
      completionMatcher = {
        case Done =>
          println("Done was passed. Terminating the system.")
          actorSystem.terminate()
          // complete stream immediately if we send it Done
          CompletionStrategy.immediately

      },
      // never fail the stream because of a message
      failureMatcher = PartialFunction.empty,
      bufferSize = 2000,
      overflowStrategy = OverflowStrategy.dropHead)

    val streamActor: ActorRef = source.to(Sink.foreach(println)).run()


    // test
    for (_ <- 0 to 100) {
      streamActor ! Candle(
        FiniteDuration(3, SECONDS),
        Figis.BTC,
        CandleDetails(25123.97, 45864.23, 31653.55, 42777.15, Instant.now())
      )

      Thread.sleep(500)
    }



    // The stream completes successfully with the following message
    streamActor ! Done
  }
}
