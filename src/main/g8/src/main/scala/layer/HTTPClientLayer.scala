package layer

import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import zio.interop.catz._
import zio.logging.Logging
import zio.{Has, Task, ZLayer, ZManaged}

object HTTPClientLayer {

  val http4sLive: ZLayer[Logging with zio.ZEnv, Nothing, Has[Client[Task]]] =
    ZManaged
      .runtime[zio.ZEnv]
      .flatMap { implicit runtime =>
        BlazeClientBuilder[Task].resource.toManagedZIO
      }
      .tapCause(e => Logging.error("http4s 初始化错误, 不太可能发生", e).toManaged_)
      .orDie
      .toLayer

}
