import sbt._

object Dependencies {

  object Versions {
    val zio = "2.0.4"
    val zioCache = "0.2.0"
    val zioConfig = "3.0.2"
    val zioLogging = "2.1.2"
    val circe = "0.14.3"
    val quill = "4.5.0"
    val postgres = "42.2.24"
    val scalaTest = "3.2.12"
    val tapir = "1.1.3"
    val http4s = "0.23.12"
  }

  val tapir = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-zio",
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server-zio",
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle",
    "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics",
    "com.softwaremill.sttp.tapir" %% "tapir-opentelemetry-metrics"
  ).map(_ % Versions.tapir)

  val monocle = Seq(
    "dev.optics" %% "monocle-core" % "3.1.0",
    "dev.optics" %% "monocle-macro" % "3.1.0"
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-blaze-client",
    "org.http4s" %% "http4s-blaze-server",
    "org.http4s" %% "http4s-core",
    "org.http4s" %% "http4s-circe"
  ).map(_ % Versions.http4s)

  val zioLogging = Seq(
    "dev.zio" %% "zio-logging" % Versions.zioLogging,
    "dev.zio" %% "zio-logging-slf4j" % Versions.zioLogging
  )

  val logging = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.11"
  ) ++ zioLogging

  val zioTest = Seq(
    "dev.zio" %% "zio-test" % Versions.zio % "test",
    "dev.zio" %% "zio-test-magnolia" % Versions.zio % "test", // optional
    "dev.zio" %% "zio-test-sbt" % Versions.zio % "test"
  )

  val zioConfig = Seq(
    "dev.zio" %% "zio-config" % Versions.zioConfig,
    "dev.zio" %% "zio-config-magnolia" % Versions.zioConfig,
    "dev.zio" %% "zio-config-typesafe" % Versions.zioConfig
  )

  val zio = Seq(
    "dev.zio" %% "zio" % Versions.zio,
    "dev.zio" %% "zio-nio" % "2.0.0",
    "dev.zio" %% "zio-streams" % Versions.zio,
    "dev.zio" %% "zio-metrics-prometheus" % "2.0.0",
    "dev.zio" %% "zio-interop-cats" % "3.3.0"
  )

  val zioFamily = zio ++ zioTest ++ zioConfig ++ zioCache

  val quill = Seq(
    "io.getquill" %% "quill-jdbc-zio"
  ).map(_ % Versions.quill)

  val postgres = Seq(
    "org.postgresql" % "postgresql" % Versions.postgres
  )

  lazy val circeFamily = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-pointer"
  ).map(_ % Versions.circe) ++ Seq(
    "org.gnieh" %% "diffson-circe" % "4.3.0"
  )

  val coreDependency =
    tapir ++ logging ++ quill ++ postgres ++ http4s ++ circeFamily ++ zioFamily ++ monocle
}
