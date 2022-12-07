package types.error

import io.circe.generic.JsonCodec

object ErrorInfos {

  sealed trait ErrorInfo {

    val message: String

    val englishMessage: String

    val detail: Option[String]

  }

  @JsonCodec
  case class NotFound(
      message: String = "not found!",
      englishMessage: String = "not found!",
      detail: Option[String]
  ) extends ErrorInfo

  @JsonCodec
  case class Unauthorized(
      message: String = "unauthorized",
      englishMessage: String = "unauthorized",
      detail: Option[String] = None
  ) extends ErrorInfo

  @JsonCodec
  case class NonAuthoritativeInformation(
      message: String = "rule engine loading!",
      englishMessage: String = "rule engine loading!",
      detail: Option[String] = None
  ) extends ErrorInfo

  @JsonCodec
  case class BadRequest(
      message: String = "rule engine loading!",
      englishMessage: String = "rule engine loading!",
      detail: Option[String] = None
  ) extends ErrorInfo

  @JsonCodec
  case class InternalServerError(
      message: String = "internal server error",
      englishMessage: String = "internal server error!",
      detail: Option[String] = None
  ) extends ErrorInfo

  @JsonCodec
  case class Forbidden(
      message: String,
      englishMessage: String,
      detail: Option[String] = None
  ) extends ErrorInfo

  @JsonCodec
  case class Conflict(
      message: String,
      englishMessage: String,
      detail: Option[String] = None
  ) extends ErrorInfo

  /*
  428 - Precondition Required
   */
  @JsonCodec
  case class PreconditionRequired(
      message: String,
      englishMessage: String,
      detail: Option[String] = None
  ) extends ErrorInfo

  /** TooManyRequests 429
    * @param message
    *   rate limit exceeded
    */
  @JsonCodec
  case class TooManyRequests(
      message: String,
      englishMessage: String,
      detail: Option[String] = None
  ) extends ErrorInfo

  @JsonCodec
  case class Locked(
      message: String,
      englishMessage: String,
      detail: Option[String] = None
  ) extends ErrorInfo

  @JsonCodec
  case class NotAcceptable(
      message: String,
      englishMessage: String,
      detail: Option[String] = None
  ) extends ErrorInfo
}
