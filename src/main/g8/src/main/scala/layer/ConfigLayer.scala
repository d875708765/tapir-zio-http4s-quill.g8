package layer

import zio._
import zio.config._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfig

object ConfigLayer {

  final case class ServerConfig(host: String, port: Int)

  final case class AppConfig(
      serverConfig: ServerConfig
  )

  val live: ZLayer[Any, Nothing, AppConfig] = {

    val appConfigAutomatic: ConfigDescriptor[AppConfig] = descriptor[AppConfig]

    val appConfigLayer: ZLayer[Any, Nothing, AppConfig] =
      TypesafeConfig.fromResourcePath(appConfigAutomatic).orDie

    ZLayer.make[AppConfig](
      appConfigLayer
    )
  }

}
