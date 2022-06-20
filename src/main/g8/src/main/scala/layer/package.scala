import layer.DatabaseLayer.Persistent
import org.http4s.client.Client
import types.config.AppConfig
import zio.logging.Logging
import zio.magic._
import zio.{Has, Task, ULayer, ZEnv, ZLayer}

package object layer {

  type Http4s = Has[Client[Task]]

  type AllEnv = ZEnv with Logging with Http4s with Persistent with CustomConfig

  type CustomConfig = Has[AppConfig]

  val http4sLayer: ZLayer[Logging with zio.ZEnv, Nothing, Has[Client[Task]]] =
    HTTPClientLayer.http4sLive

  val loggingLayer: ULayer[Logging] = LoggingLayer.live

  val all: ZLayer[Any, Nothing, AllEnv] = ZLayer.wire[AllEnv](
    ZEnv.live,
    loggingLayer,
    http4sLayer,
    ConfigLayer.live,
    DatabaseLayer.quill.live
  )

}
