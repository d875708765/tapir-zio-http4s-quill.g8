package routes

import layer.AllEnv
import org.http4s.HttpRoutes
import sttp.model.StatusCode
import sttp.tapir.generic.auto.schemaForCaseClass
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import sttp.tapir.ztapir._
import types.error.ErrorInfos._
import zio.{RIO, ZIO}

object SystemRoutes {

  val base = endpoint
    .tag("System")
    .in("system")

  val versionEndpoint=
    base.get
      .in("version")
      .out(jsonBody[String])
      .errorOut(
        oneOf[ErrorInfo](
          oneOfVariant(
            StatusCode.InternalServerError,
            jsonBody[InternalServerError].description("internal server error")
          )
        )
      )
      .summary("获取版本信息")
      .description("获取版本信息")
      .zServerLogic(_ => versionLogic)

  def versionLogic: ZIO[Any, ErrorInfo, String] = {
    ZIO.succeed(buildInfo.build.BuildInfo.toString)
  }

  val allEndpoints = List(
    versionEndpoint.widen[AllEnv]
  )

  val allRoutes: HttpRoutes[RIO[AllEnv, *]] =
    ZHttp4sServerInterpreter()
      .from(
        allEndpoints
      )
      .toRoutes

}
