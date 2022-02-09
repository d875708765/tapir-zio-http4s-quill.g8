package layer

import io.getquill.context.ZioJdbc.DataSourceLayer
import zio.{Has, ZLayer}

import javax.sql.DataSource

object DatabaseLayer {

  type Persistent = Has[DataSource]

  object quill {

    def live: ZLayer[Any, Nothing, Persistent] =
      DataSourceLayer.fromPrefix("ctx").orDie
  }

}
