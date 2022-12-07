import layer.DatabaseLayer.Persistent
import org.http4s.client.Client
import zio.logging.Logging
import layer.ConfigLayer
import zio.magic._
import zio.{Has, Task, ULayer, ZEnv, ZLayer}

package object layer {

  type CustomConfig = AppConfig

  type AllEnv = Logging with Http4s with Persistent with CustomConfig

  type Http4s = Client[Task]

  val http4sLayer: ZLayer[Any, Nothing, Client[Task]] =
    HTTPClientLayer.Http4s.live

  val loggingLayer: ZLayer[Any, Nothing, Unit] = LoggingLayer.live

  val databaseLayer: ZLayer[Any, Nothing, Persistent] = DatabaseLayer.quill.live


  val all: ZLayer[Any, Nothing, AllEnv] = ZLayer.make[AllEnv](
    loggingLayer,
    http4sLayer,
    ConfigLayer.live,
    databaseLayer
  )

}
