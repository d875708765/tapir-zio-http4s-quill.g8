package layer

import zio.ULayer
import zio.logging._
import zio.logging.slf4j._

object LoggingLayer {
  val live: ULayer[Logging] = Slf4jLogger.make { (_, message) =>
    message
  }
}
