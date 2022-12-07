package layer

import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import sttp.capabilities
import sttp.capabilities.zio.ZioStreams
import sttp.client3.httpclient.zio.HttpClientZioBackend
import sttp.client3.{SttpBackend, SttpBackendOptions}
import zio.interop.catz._
import zio.{Task, ZIO, ZLayer}

import java.net.http.HttpClient
import scala.concurrent.duration.DurationInt

object HTTPClientLayer {

  object Http4s {

    val live: ZLayer[Any, Nothing, Client[Task]] = ZLayer
      .scoped(
        BlazeClientBuilder[Task]
          .withConnectTimeout(3.second)
          .withRequestTimeout(30.second)
          .withMaxTotalConnections(500)
          .withMaxConnectionsPerRequestKey(
            Function.const(400)
          )
          .withMaxWaitQueueLimit(512)
          .resource
          .toScopedZIO
      )
      .tapErrorCause(e => ZIO.logErrorCause("init http4s client failed", e))
      .orDie
  }

}
