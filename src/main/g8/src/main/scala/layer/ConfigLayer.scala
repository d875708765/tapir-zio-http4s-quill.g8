package layer

import zio.config._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfig
import zio._

object ConfigLayer {

  final case class ServerConfig(host: String, port: Int)

  final case class AppConfig(
                              serverConfig: ServerConfig
                            )

  val live: ZLayer[Any, Nothing, Has[AppConfig]] = {

    val appConfigAutomatic: ConfigDescriptor[AppConfig] = descriptor[AppConfig]

    val appConfigLayer: ZLayer[Any, Nothing, AppConfig] =
      TypesafeConfig.fromResourcePath(appConfigAutomatic).orDie

    val appConfigLayer = AppConfig.live.orDie

    ZLayer.make[AppConfig](
      appConfigLayer
    )
  }

}
