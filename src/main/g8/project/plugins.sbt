addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.4")

addSbtPlugin("com.lightbend.sbt" % "sbt-javaagent" % "0.1.6")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.10.0")

// project/plugins.sbt sbt "scalafix RemoveUnused"
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.1")

//sbt-scalafmt https://github.com/scalameta/sbt-scalafmt
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
