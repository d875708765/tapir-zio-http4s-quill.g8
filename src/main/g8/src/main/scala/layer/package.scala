import application.AppConfig
import application.AppConfig.appConfigLayer
import layer.DatabaseLayer.Persistent
import org.http4s.client.Client
import zio.config.ReadError
import zio.logging.Logging
import zio.{Has, Layer, Task, ULayer, ZEnv, ZLayer}

package object layer {

  type Http4s = Has[Client[Task]]

  type AllEnv = ZEnv
    with Logging
    with Has[AppConfig]
    with Http4s
    with Persistent

  val http4sLayer: ZLayer[Logging with zio.ZEnv, Nothing, Has[Client[Task]]] =
    HTTPClientLayer.http4sLive

  val configLayer: Layer[ReadError[String], Has[AppConfig]] =
    appConfigLayer.orDie

  val loggingLayer: ULayer[Logging] = LoggingLayer.live


  val all: ZLayer[Any, Throwable, AllEnv] =
    (ZEnv.live >+> loggingLayer >+> http4sLayer >+> configLayer >+> DatabaseLayer.quill.live)
}
