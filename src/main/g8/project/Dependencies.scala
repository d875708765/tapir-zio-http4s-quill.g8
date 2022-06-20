import sbt._

object Dependencies {

  object Versions {
    val zio = "1.0.14"
    val zioConfig = "2.0.4"
    val zioMagic = "0.3.8"
    val zioLogging = "0.5.14"
    val akka = "2.6.19"
    val akkaManagement = "1.1.3"
    val akkaProjection = "1.2.4"
    val circe = "0.14.2"
    val quill = "3.16.5"
    val postgres = "42.2.24"
    val scalaTest = "3.2.12"
    val tapir = "0.20.0-M4"
    val http4s = "0.23.12"
    val sttp = "3.5.2"
  }


  val tapir = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-zio",
    "com.softwaremill.sttp.tapir" %% "tapir-zio-http4s-server",
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml",
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle",
    "com.softwaremill.sttp.tapir" %% "tapir-redoc-bundle",
    "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics",
    "com.softwaremill.sttp.tapir" %% "tapir-opentelemetry-metrics"
  ).map(_ % Versions.tapir)


  val http4s = Seq(
    "org.http4s" %% "http4s-blaze-client",
    "org.http4s" %% "http4s-blaze-server",
    "org.http4s" %% "http4s-core",
    "org.http4s" %% "http4s-circe"
  ).map(_ % Versions.http4s)

  val zioLoggingVersion = "0.5.12"

  val ZioConfigVersion = "1.0.5"


  val zioLogging = Seq(
    "dev.zio" %% "zio-logging"       % Versions.zioLogging,
    "dev.zio" %% "zio-logging-slf4j" % Versions.zioLogging,
  )

  val logging = Seq(
    "ch.qos.logback"                 % "logback-classic" % "1.2.11"
  ) ++ zioLogging


  val zioTest = Seq(
    "dev.zio" %% "zio-test"          % Versions.zio % "test",
    "dev.zio" %% "zio-test-magnolia" % Versions.zio % "test", // optional
    "dev.zio" %% "zio-test-sbt"      % Versions.zio % "test"
  )

  val zioConfig = Seq(
    "dev.zio" %% "zio-config"          % Versions.zioConfig,
    "dev.zio" %% "zio-config-magnolia" % Versions.zioConfig,
    "dev.zio" %% "zio-config-typesafe" % Versions.zioConfig
  )


  val quill = Seq(
    "io.getquill" %% "quill-jdbc-zio",
    "io.getquill" %% "quill-core"
  ).map(_ % Versions.quill)


  val postgres = Seq(
    "org.postgresql" % "postgresql" % Versions.postgres
  )

  val zio = Seq(
    "dev.zio" %% "zio"                         % Versions.zio,
    "dev.zio" %% "zio-nio"                     % "1.0.0-RC12",
    "dev.zio" %% "zio-streams"                 % Versions.zio,
    "dev.zio" %% "zio-interop-reactivestreams" % "1.3.12",
    "dev.zio" %% "zio-metrics-prometheus"      % "1.0.14",
    "dev.zio" %% "zio-zmx"                     % "0.0.13",
    "io.github.kitlangton" %% "zio-magic"      % "0.3.12"
  )


  val zioFamily = zio ++ zioTest ++ zioConfig


  lazy val circeFamily = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic-extras"
  ).map(_ % Versions.circe) :+ "io.circe" %% "circe-config" % "0.8.0"



  val coreDependency =
    tapir ++ logging ++ quill ++ postgres ++ http4s ++ circeFamily  ++ zioFamily
}
