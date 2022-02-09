package routes

import layer.AllEnv
import org.http4s.HttpRoutes
import sttp.model.StatusCode
import sttp.tapir.generic.auto.schemaForCaseClass
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import sttp.tapir.ztapir._
import types.error.ErrorInfos._
import zio.logging.Logging
import zio.{RIO, ZIO}

object SystemRoutes {

  val base = endpoint
    .tag("System")
    .in("system")

  val versionEndpoint: _root_.sttp.tapir.ztapir.ZServerEndpoint[Logging, Any] =
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

  def versionLogic: ZIO[Logging, ErrorInfo, String] = {
    val io = for {
      buildInfo <- ZIO.effect(buildInfo.build.BuildInfo)
    } yield buildInfo.toString

    io.tapCause(Logging.error("get app version error", _))
      .catchAll(_ => ZIO fail InternalServerError())
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
