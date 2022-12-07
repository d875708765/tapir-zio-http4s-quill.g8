package application

import cats.data.Kleisli
import layer.AllEnv
import layer.ConfigLayer.AppConfig
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import org.http4s.{Request, Response}
import zio.config.getConfig
import zio.interop.catz._
import zio.{RIO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object Loader extends ZIOAppDefault {

  override def run: ZIO[ZIOAppArgs with Scope, Throwable, Unit] = {

    val allRoutes: Kleisli[RIO[AllEnv, *], Request[RIO[AllEnv, *]], Response[
      RIO[AllEnv, *]
    ]] = Router(
      "/" -> AllRoutes
    ).orNotFound

    /** web 服务
      */
    val server: ZIO[AllEnv, Throwable, Unit] = {
      import scala.concurrent.duration.DurationInt
      ZIO.executorWith[AllEnv, Throwable, Unit] { executor =>
        for {
          appConfig <- getConfig[AppConfig]
          serverConfig = appConfig.serverConfig
          _ <- BlazeServerBuilder[RIO[AllEnv, *]]
            .withExecutionContext(executor.asExecutionContext)
            .bindHttp(serverConfig.port, serverConfig.host)
            .withIdleTimeout(5.minute)
            .withResponseHeaderTimeout(3.minute)
            .withHttpApp(allRoutes)
            .serve
            .compile
            .drain
        } yield ()
      }
    }

    (server).unit
      .provideLayer(all)
  }
}
