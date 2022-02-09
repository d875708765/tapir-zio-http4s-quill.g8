package application

import zio.config._
import ConfigDescriptor._
import com.typesafe.config.ConfigFactory
import zio.config.typesafe.TypesafeConfig.fromTypesafeConfig
import zio.{Has, Layer}

final case class ServerConfig(host: String, port: Int)

final case class AppConfig(
    serverConfig: ServerConfig
)

object AppConfig {

  val serverConfigDescriptor: ConfigDescriptor[ServerConfig] = (
    string("host") |@|
      int("port")
  )(ServerConfig.apply, ServerConfig.unapply)

  val appConfigDescriptor: ConfigDescriptor[AppConfig] = (
    nested("serverConfig") { serverConfigDescriptor }
  )(
    AppConfig.apply,
    AppConfig.unapply
  )

  val appConfigLayer: Layer[ReadError[String], Has[AppConfig]] =
    fromTypesafeConfig(ConfigFactory.load(), appConfigDescriptor)

}
