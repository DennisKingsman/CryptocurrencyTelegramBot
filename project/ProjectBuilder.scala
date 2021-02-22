import com.typesafe.sbt.SbtNativePackager
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import com.typesafe.sbt.packager.universal.UniversalPlugin
import sbt._
import sbt.Keys._
import spray.revolver.RevolverPlugin.autoImport.reStart

object ProjectBuilder {

  private val compilerOptions = Seq(
    "-deprecation",
    "-encoding",
    "utf-8",
    "-explaintypes",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xcheckinit",
    "-Xfuture",
    "-Xlint:adapted-args",
    "-Xlint:by-name-right-associative",
    "-Xlint:constant",
    "-Xlint:delayedinit-select",
    "-Xlint:doc-detached",
    "-Xlint:inaccessible",
    "-Xlint:infer-any",
    "-Xlint:missing-interpolator",
    "-Xlint:nullary-override",
    "-Xlint:nullary-unit",
    "-Xlint:option-implicit",
    "-Xlint:package-object-classes",
    "-Xlint:poly-implicit-overload",
    "-Xlint:private-shadow",
    "-Xlint:stars-align",
    "-Xlint:unsound-match",
    "-Yno-adapted-args",
    "-Ypartial-unification",
    "-Ywarn-dead-code",
    "-Ywarn-extra-implicit",
    "-Ywarn-inaccessible",
    "-Ywarn-infer-any",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit",
    "-Ywarn-numeric-widen",
  )

  private val compilerPlugins = Seq(
    compilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    compilerPlugin("org.typelevel" %% "kind-projector" % "0.11.3" cross CrossVersion.full),
    compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )

  private val ignoreScalaDocs = Seq(
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in packageDoc := false,
    sources in (Compile, doc) := Seq.empty
  )

  private val commonPlugins = Seq(
    SbtNativePackager,
    UniversalPlugin,
    JavaAppPackaging,
  )

  private val commonSettings = Seq(
    scalaVersion := "2.12.13",
    scalacOptions := compilerOptions,
    libraryDependencies ++= compilerPlugins,
    reStart / javaOptions += "-Dconfig.file=src/main/resources/local.conf",
    testOnly / aggregate := false,
    resolvers ++= Resolvers.common
  ) ++ ignoreScalaDocs

  def service(name: String): Project =
    Project(name, file(s"service/$name/app"))
      .enablePlugins(commonPlugins: _*)
      .settings(commonSettings)
      .settings(libraryDependencies ++= Dependencies.all)

  def common(name: String): Project =
    Project(s"common-$name", file(s"common/$name"))
      .enablePlugins(commonPlugins: _*)
      .settings(commonSettings)
      .settings(libraryDependencies ++= Dependencies.all)

}
