package layer

import io.getquill.jdbczio.Quill
import zio.{ZIO, ZLayer}

import javax.sql.DataSource

object DatabaseLayer {

  type Persistent = DataSource

  object quill {

    def live: ZLayer[Any, Nothing, Persistent] =
      Quill.DataSource
        .fromPrefix("ctx")
        .tapErrorCause(e => ZIO.logErrorCause("init data source failed", e))
        .orDie

  }

}
