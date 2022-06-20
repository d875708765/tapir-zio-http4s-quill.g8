package layer

import types.config.AppConfig
import zio.{Has, ZLayer}
import zio.magic._


object ConfigLayer {

  val live: ZLayer[Any, Nothing, Has[AppConfig]] = {

    val appConfigLayer = AppConfig.live.orDie

    ZLayer.wire[Has[AppConfig]](
      appConfigLayer
    )
  }

}
