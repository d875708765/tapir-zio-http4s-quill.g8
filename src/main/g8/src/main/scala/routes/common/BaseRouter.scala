package routes.common

import sttp.model.StatusCode
import sttp.tapir.{Endpoint, EndpointOutput}
import sttp.tapir.generic.auto.schemaForCaseClass
import sttp.tapir.json.circe._
import sttp.tapir.ztapir._
import types.error.ErrorInfos._

trait BaseRouter {

  private val errorInfoEndpointOutput
      : EndpointOutput.OneOf[ErrorInfo, ErrorInfo] = oneOf[ErrorInfo](
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
      StatusCode.NonAuthoritativeInformation,
      jsonBody[NonAuthoritativeInformation].description(
        "rule engine loading!"
      )
    ),
    oneOfVariant(
      StatusCode.PreconditionRequired,
      jsonBody[PreconditionRequired].description("Precondition Required")
    ),
    oneOfVariant(
      StatusCode.TooManyRequests,
      jsonBody[TooManyRequests].description("rate limit exceeded")
    ),
    oneOfVariant(
      StatusCode.Conflict,
      jsonBody[Conflict].description("Conflict")
    ),
    oneOfVariant(
      StatusCode.NotFound,
      jsonBody[NotFound].description("NotFound")
    ),
    oneOfVariant(
      StatusCode.Unauthorized,
      jsonBody[Unauthorized].description("Unauthorized")
    ),
    oneOfVariant(
      StatusCode.Locked,
      jsonBody[Locked].description("Locked")
    ),
    oneOfVariant(
      StatusCode.NotAcceptable,
      jsonBody[NotAcceptable].description("NotAcceptable")
    )
  )
  protected val baseRouter: Endpoint[Unit, Unit, ErrorInfo, Unit, Any] =
    endpoint
      .errorOut(
        errorInfoEndpointOutput
      )

}
