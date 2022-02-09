import sbt._

object Dependencies {

  val tapirVersion = "0.20.0-M4"

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
  ).map(_ % tapirVersion)

  val http4sVersion = "0.23.7"

  val http4s = Seq(
    "org.http4s" %% "http4s-blaze-client",
    "org.http4s" %% "http4s-blaze-server",
    "org.http4s" %% "http4s-core",
    "org.http4s" %% "http4s-circe"
  ).map(_ % http4sVersion)

  val zioLoggingVersion = "0.5.12"

  val ZioConfigVersion = "1.0.5"

  val log = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.6",
    "dev.zio" %% "zio-logging" % zioLoggingVersion,
    "dev.zio" %% "zio-logging-slf4j" % zioLoggingVersion,
    "dev.zio" %% "zio-config" % ZioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % ZioConfigVersion,
    "dev.zio" %% "zio" % "1.0.2",
    "dev.zio" %% "zio-interop-cats" % "2.2.0.1",
    "dev.zio" %% "zio-nio" % "1.0.0-RC11"
  )

  val quillVersion = "3.11.0"

  val quill = Seq(
    "io.getquill" %% "quill-jdbc-zio" % quillVersion,
    "io.getquill" %% "quill-core" % quillVersion
  )

  val postgresVersion = "42.2.24"

  val postgres = Seq(
    "org.postgresql" % "postgresql" % postgresVersion
  )

//  val redis4CatsVersion = "1.0.0"
//
//  val cache = Seq(
//    "dev.profunktor" %% "redis4cats-effects" % redis4CatsVersion
//  )

  val circeVersion = "0.14.1"

  lazy val zioFamily = Seq(
    "dev.zio" %% "zio-streams" % "1.0.2",
    "dev.zio" %% "zio-kafka" % "0.17.1",
    "dev.zio" %% "zio-opentracing" % "0.9.0"
  )

//  val opentracingVersion = "0.33.0"
//
//  val jaegerVersion = "1.7.0"
//
//  val zipkinVersion = "2.16.3"
//
//  lazy val opentracingFamily = Seq(
//    "io.opentracing" % "opentracing-api" % opentracingVersion,
//    "io.opentracing" % "opentracing-noop" % opentracingVersion,
//    "io.opentracing" % "opentracing-mock" % opentracingVersion % Test,
//    "io.opentracing.contrib" % "opentracing-kafka-client" % "0.1.15",
//    // zipkin
//    "io.jaegertracing" % "jaeger-core" % jaegerVersion,
//    "io.jaegertracing" % "jaeger-client" % jaegerVersion,
//    "io.jaegertracing" % "jaeger-zipkin" % jaegerVersion,
//    "io.zipkin.reporter2" % "zipkin-reporter" % zipkinVersion,
//    "io.zipkin.reporter2" % "zipkin-sender-okhttp3" % zipkinVersion
//  )

  lazy val circeFamily = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic-extras"
  ).map(_ % circeVersion) :+ "io.circe" %% "circe-config" % "0.8.0"

//
//  val cloudEvents = Seq(
//    "io.cloudevents" % "cloudevents-kafka" % "2.2.1"
//  )

  val coreDependency =
    tapir ++ log ++ quill ++ postgres ++ http4s ++ circeFamily  ++ zioFamily
}
