package rht.hack

import akka.actor.ActorRef
import cats.effect.{ExitCode, Resource}
import monix.eval.{Task, TaskApp}
import rht.hack.components.{ConfigComponent, KafkaCandlesReader}
import tofu.logging.Logs

trait HackathonApp extends TaskApp {

  type SourceActor = ActorRef

  protected def start(args: List[String]): SourceActor

  final override def run(args: List[String]): Task[ExitCode] = {
    implicit val logs = Logs.sync[Task, Task]
    implicit val s = scheduler
    val config = ConfigComponent()

    val program =
      for {
        actorRef <- Resource.liftF(Task.delay(start(args)))
        consumerBuilder = KafkaCandlesReader.mkBuilder(config)
        _ <- consumerBuilder(actorRef)
      } yield ()

    program.use(_ => Task(ExitCode.Success))
  }

}
