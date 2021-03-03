package rht.hack.bot

import io.circe._
import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{AkkaTelegramBot, Webhook}
import com.bot4s.telegram.clients.AkkaHttpClient

import scala.collection.mutable
import scala.concurrent.Future


// Do not use! Not working!
// An attempt to write a telegram bot in scala.

object CryptoBot extends AkkaTelegramBot
  with Webhook
  with Commands[Future] {

  val botToken = "TOKEN"
  val client = new AkkaHttpClient(botToken)
  override val port = 5432
  override val webhookUrl = "localhost"//"https://f4280b11c219.ngrok.io"

  val rng = new scala.util.Random(System.currentTimeMillis())

  onCommand("Hi") { implicit msg =>
    withArgs { args =>
      reply("Hello").void
    }
  }

  onCommand("hi") { implicit msg =>
    reply("Guten tag!").void
  }

  onCommand("coin" or "flip") { implicit msg =>
    reply(if (rng.nextBoolean()) "Head!" else "Tail!").void
  }

  onCommand("btc") { implicit msg =>
    val sched = SchedulerExtractor.start("btc", msg.chat.id, botToken)
    reply("Guten tag!").void
  }
}


