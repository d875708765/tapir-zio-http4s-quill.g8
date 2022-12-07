package types.error

sealed trait Error extends Exception {

  /** http status code
    */
  val statusCode: Int = 500

  final override def fillInStackTrace(): Throwable = this

  /** error message
    */
  val message: String = ""

  /** english error message
    */
  val englishMessage: String = ""

  /** error reason
    */
  val reason: Error.Reason = Error.Reason.Unit

  override final def getMessage: String =
    s"""
       |message: \${message}
       |reason: \${reason.getMessage}
       |""".stripMargin
}

object Error {

  sealed abstract class Reason {

    override def toString: String = super.toString

    def getMessage: String
  }

  object Reason {

    object Unit extends Reason {
      override final def toString: String = "unit"

      override def getMessage: String = "unit"
    }

    final case class Str(message: String) extends Reason {
      override def toString: String = message

      override def getMessage: String = message
    }

    final case class HttpResponse(
        method: String,
        uri: String,
        status: Int,
        headers: Map[String, String],
        body: Option[String],
        responseBody: Option[String]
    ) extends Reason {

      override def toString: String =
        s"""
           |method: \${method}
           |uri: \${uri}
           |headers: \${headers}
           |body: \${body.getOrElse("")}
           |status: \${status}
           |responseBody: \${responseBody.getOrElse("")}
           |""".stripMargin

      /** ignore body
        * @return
        */
      override def getMessage: String =
        s"""
           |method: \${method}
           |uri: \${uri}
           |headers: \${headers}
           |status: \${status}
           |responseBody: \${responseBody}
           |""".stripMargin
    }

    final case class Permission(
        tenantId: String,
        userId: String,
        permissionAlias: String,
        openId: String,
        `type`: String
    ) extends Reason {

      override def toString: String =
        s"""
           |tenantId: \${tenantId}
           |userId: \${userId}
           |permissionAlias: \${permissionAlias}
           |openId: \${openId}
           |type: \${`type`}
           |""".stripMargin

      override def getMessage: String = toString
    }

    final case class ExpectedResult(
        expected: String,
        actual: String
    ) extends Reason {
      override def toString: String =
        s"""
           |expected: \${expected}
           |actual: \${actual}
           |""".stripMargin

      override def getMessage: String = toString
    }

  }

  /** http unexpected response
    * @param statusCode
    *   http status code
    * @param message
    *   error message
    * @param reason
    *   error reason
    */
  final case class UnexpectedResponse(
      override val statusCode: Int,
      override val message: String = "请求失败！",
      override val englishMessage: String = "request failed!",
      override val reason: Reason.HttpResponse
  ) extends Error {

    def withMessage(message: String): UnexpectedResponse =
      copy(message = message)

    def withEnglishMessage(englishMessage: String): UnexpectedResponse =
      copy(englishMessage = englishMessage)

    def withAllMessage(
        message: String,
        englishMessage: String
    ): UnexpectedResponse =
      copy(message = message, englishMessage = englishMessage)

  }

  /** insufficient permission
    *
    * @param statusCode
    *   http status code
    * @param message
    *   error message
    * @param englishMessage
    *   english error message
    * @param reason
    *   error reason
    */
  final case class InsufficientPermission(
      override val statusCode: Int = 403,
      override val message: String = "权限不足！",
      override val englishMessage: String = "Insufficient permission!",
      override val reason: Reason.Permission
  ) extends Error

  final case class JsonDecoderError(
      override val statusCode: Int = 500,
      override val message: String = "数据格式有误！",
      override val englishMessage: String = "json decoder fail!",
      override val reason: Reason.Str
  ) extends Error

  final case class ResourceAlreadyExist(
      override val statusCode: Int = 409,
      override val message: String = "资源已经存在！",
      override val englishMessage: String = "resource already exist!",
      override val reason: Reason.Str
  ) extends Error

  final case class WrongFormat(
      override val statusCode: Int = 400,
      override val message: String = "数据格式有误！",
      override val englishMessage: String = "wrong data format!",
      override val reason: Reason.Str
  ) extends Error

  final case class ResourceLimit(
      override val statusCode: Int = 428,
      override val message: String = "资源不足！",
      override val englishMessage: String = "lack of resources!",
      override val reason: Reason.Str
  ) extends Error

  final case class TypeNotSupport(
      override val statusCode: Int = 400,
      override val message: String = "类型不支持！",
      override val englishMessage: String = "type not support!",
      override val reason: Reason.Str
  ) extends Error

  final case class ResourceNotExist(
      override val statusCode: Int = 404,
      override val message: String = "资源不存在！",
      override val englishMessage: String = "resource does not exist!",
      override val reason: Reason.Str
  ) extends Error

  final case class ResourceAlreadyLock(
      override val statusCode: Int = 423,
      override val message: String = "资源已锁定不编辑！",
      override val englishMessage: String =
        "resource already locked does not edit!",
      override val reason: Reason.Str
  ) extends Error

  final case class ConfigNotExist(
      override val statusCode: Int = 500,
      override val message: String = "配置不存在！",
      override val englishMessage: String = "config does not exist!",
      override val reason: Reason.Str
  ) extends Error

  final case class UnExpectedResult(
      override val statusCode: Int = 500,
      override val message: String = "不是期待的返回结果！",
      override val englishMessage: String = "not the expected return result!",
      override val reason: Reason.ExpectedResult
  ) extends Error

  final case class OperationProhibited(
      override val statusCode: Int = 403,
      override val message: String = "禁止操作！",
      override val englishMessage: String = "operation prohibited!",
      override val reason: Reason.Str
  ) extends Error

}
