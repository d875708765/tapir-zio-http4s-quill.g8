package routes.common

import sttp.model.StatusCode
import sttp.tapir.Endpoint
import sttp.tapir.generic.auto.schemaForCaseClass
import sttp.tapir.json.circe._
import sttp.tapir.ztapir._
import types.error.ErrorInfos._

trait BaseRouter {

  protected val baseRouter: Endpoint[Unit, Unit, ErrorInfo, Unit, Any] =
    endpoint
      .errorOut(
        oneOf[ErrorInfo](
          oneOfVariant(
            StatusCode.NotFound,
            jsonBody[NotFound].description("NotFound")
          ),
          oneOfVariant(
            StatusCode.BadRequest,
            jsonBody[BadRequest].description("BadRequest")
          ),
          oneOfVariant(
            StatusCode.InternalServerError,
            jsonBody[InternalServerError].description("internal server error")
          ),
          oneOfVariant(
            StatusCode.Forbidden,
            jsonBody[Forbidden].description("Forbidden")
          ),
          oneOfVariant(
            StatusCode.Conflict,
            jsonBody[Conflict].description("Conflict")
          ),
          oneOfVariant(
            StatusCode.NotFound,
            jsonBody[NotFound].description("NotFound")
          )
        )
      )

}
