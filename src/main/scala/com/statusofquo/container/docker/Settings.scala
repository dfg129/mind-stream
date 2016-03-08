package com.statusofquo.container
package docker

import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import akka.actor.ExtendedActorSystem
import com.typesafe.config.Config

class SettingsExtension(config: Config) extends Extension {
  val dockerInfoReq: String = config.getString("container.info.uri")

  val dbCntnrStart: String = config.getString("container.db.start.uri")
}

object Settings extends ExtensionId[SettingsExtension] with ExtensionIdProvider {

  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) =
    new SettingsExtension(system.settings.config)
}
