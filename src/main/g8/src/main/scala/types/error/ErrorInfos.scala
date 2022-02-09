package types.error

import io.circe.Json
import io.circe.generic.JsonCodec

import java.time.LocalDateTime

object ErrorInfos {

  sealed trait ErrorInfo

  @JsonCodec
  case class GlobalResultError(
      data: Json,
      timestamp: LocalDateTime = LocalDateTime.now(),
      message: String = "success!"
  ) extends ErrorInfo

  @JsonCodec
  case class GlobalPageResultError(
      data: Json,
      current: Int,
      pageSize: Int,
      total: Long,
      timestamp: LocalDateTime = LocalDateTime.now(),
      message: String = "success!"
  ) extends ErrorInfo

  @JsonCodec
  case class ResultOk(
      data: Json,
      timestamp: LocalDateTime = LocalDateTime.now(),
      message: String = "success!"
  ) extends ErrorInfo

  @JsonCodec
  case class NotFound(message: String) extends ErrorInfo

  @JsonCodec
  case class Unauthorized(message: String = "unauthorized") extends ErrorInfo

  @JsonCodec
  case class BadRequest(message: String) extends ErrorInfo

  @JsonCodec
  case class InternalServerError(message: String = "internal server error")
      extends ErrorInfo

  @JsonCodec
  case class Unknown(message: String = "Unknown") extends ErrorInfo

  @JsonCodec
  case class Forbidden(message: String) extends ErrorInfo

  @JsonCodec
  case class Conflict(message: String) extends ErrorInfo
}
