import sbt.Resolver

object Resolvers {

  val common = Seq(
    Resolver.sonatypeRepo("releases")
  )

}
