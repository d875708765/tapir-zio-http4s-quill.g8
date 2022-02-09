package application

import cats.data.Kleisli
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import org.http4s.{Request, Response}
import zio.config.getConfig
import zio.interop.catz._
import zio.{ExitCode, RIO, URIO, ZIO}

object Loader extends CatsApp {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    val allRoutes: Kleisli[RIO[AllEnv, *], Request[RIO[AllEnv, *]], Response[
      RIO[AllEnv, *]
    ]] = Router(
      "/" -> AllRoutes
    ).orNotFound

    /** web 服务
      */
    val server: ZIO[AllEnv, Throwable, Unit] =
      ZIO.runtime[AllEnv].flatMap { implicit runtime =>
        for {
          serverConfig <- getConfig[AppConfig].map(_.serverConfig)
          _ <- BlazeServerBuilder[RIO[AllEnv, *]]
            .withExecutionContext(runtime.platform.executor.asEC)
            .bindHttp(serverConfig.port, serverConfig.host)
            .withHttpApp(allRoutes)
            .serve
            .compile
            .drain
        } yield ()
      }

    (server)
      .provideLayer(all)
      .exitCode
  }
}
