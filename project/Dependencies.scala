import sbt._
import sbt.Keys._

object Dependencies {

  lazy val monix = Seq(
    "io.monix" %% "monix" % versions.monix
  )

  lazy val cats = Seq(
    "org.typelevel" %% "cats-core" % versions.cats,
    "org.typelevel" %% "cats-effect" % versions.cats,
  )

  lazy val tethys = Seq(
    "com.tethys-json" %% "tethys" % versions.tethys,
    "com.tethys-json" %% "tethys-derivation" % versions.tethys,
    "com.tethys-json" %% "tethys-enumeratum" % versions.tethys,
  )
  lazy val `kafka-fs2` = Seq(
    "com.github.fd4s" %% "fs2-kafka" % versions.`kafka-fs2`
  )

  lazy val config = Seq(
    "com.typesafe" % "config" % "1.4.0",
    "com.github.pureconfig" %% "pureconfig" % "0.14.0"
  )

  lazy val logback = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3" % Test,
    "ch.qos.logback" % "logback-core" % "1.2.3",
  )

  lazy val slf4j = Seq(
    "org.slf4j" % "slf4j-api" % "1.7.30",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.30",
    "org.slf4j" % "jcl-over-slf4j" % "1.7.30",
    "org.slf4j" % "jul-to-slf4j" % "1.7.30",
  )

  lazy val tofu = Seq(
    "ru.tinkoff" %% "tofu" % versions.tofu,
    "ru.tinkoff" %% "tofu-logging" % versions.tofu,
  )

  lazy val `scala-logging` = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  )

  lazy val enumeratum = Seq(
    "com.beachape" %% "enumeratum" % versions.enumeratum
  )

  lazy val newtype = Seq(
    "io.estatico" %% "newtype" % versions.newtype
  )

  lazy val derevo = Seq(
    "org.manatki" %% "derevo-tethys" % "0.11.2",
    "org.manatki" %% "derevo-pureconfig" % "0.11.2"
  )

  lazy val http4s = Seq(
    "org.http4s" %% "http4s-dsl" % versions.http4s,
    "org.http4s" %% "http4s-blaze-server" % versions.http4s,
    "org.http4s" %% "http4s-blaze-client" % versions.http4s
  )

  lazy val `akka-streams` = Seq(
    "com.typesafe.akka" %% "akka-stream" % versions.akka
  )

  lazy val all = Seq(
    // effects
    monix,
    // kafka
    `kafka-fs2`,
    // logging
    `scala-logging`,
    // other
    enumeratum,
    config,
    newtype,
    logback ++
      cats ++
      tofu ++
      tethys ++
      derevo
  ).flatten

  object versions {
    val cats = "2.1.0"
    val http4s = "0.21.18"
    val sttp = "2.0.0-RC9"
    val monix = "3.1.0"
    val `kafka-fs2` = "1.0.0"
    val circe = "0.13.0"
    val tethys = "0.11.0"
    val tofu = "0.7.4"
    val enumeratum = "1.6.0"
    val newtype = "0.4.4"
    val akka = "2.6.12"
  }

}
