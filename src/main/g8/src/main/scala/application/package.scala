import cats.implicits._
import org.http4s.HttpRoutes
import routes.SystemRoutes
import sttp.apispec.openapi.Info
import layer.AllEnv
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import zio.interop.catz._
import zio.{RIO, ZLayer}

package object application {


  val all: ZLayer[Any, Throwable, AllEnv] = layer.all

  val swaggerEndpoints = SwaggerInterpreter().fromServerEndpoints(
    SystemRoutes.allEndpoints,
    Info(
      title = buildInfo.build.BuildInfo.name,
      version = buildInfo.build.BuildInfo.version
    )
  )

  val swaggerRoute: HttpRoutes[RIO[AllEnv, *]] =
    ZHttp4sServerInterpreter().from(swaggerEndpoints).toRoutes

  val AllRoutes: HttpRoutes[RIO[AllEnv, *]] =
    (swaggerRoute <+> SystemRoutes.allRoutes)
}
