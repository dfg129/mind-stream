package com.statusofquo.container
package docker

import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import akka.actor.ExtendedActorSystem
import com.typesafe.config.Config

class SettingsExtension(config: Config) extends Extension {
  val uri: String = config.getString("container.info.uri")
}

object Settings extends ExtensionId[SettingsExtension] with ExtensionIdProvider {

  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) =
    new SettingsExtension(system.settings.config)
}
