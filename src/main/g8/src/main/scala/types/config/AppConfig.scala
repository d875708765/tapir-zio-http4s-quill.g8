package types.config

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

  private val serverConfigDescriptor: ConfigDescriptor[ServerConfig] = (
    string("host") zip
      int("port")
    ).to[ServerConfig]

  private val appConfigDescriptor: ConfigDescriptor[AppConfig] = (
    nested("serverConfig") { serverConfigDescriptor }
    ).to[AppConfig]

  val live: Layer[ReadError[String], Has[AppConfig]] =
    fromTypesafeConfig(ConfigFactory.load(), appConfigDescriptor)

}
