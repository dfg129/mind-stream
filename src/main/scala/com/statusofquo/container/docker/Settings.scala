package com.statusofquo.container
package docker

import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import akka.actor.ExtendedActorSystem
import akka.http.scaladsl.model.HttpMethod
import akka.http.scaladsl.model.HttpMethods._
import com.typesafe.config.{ Config, ConfigFactory }



case class CntnrCmds(cntnr: String, cntnrCreate: (String, HttpMethod), cntnrRemove: (String, HttpMethod), cntnrStart: (String, HttpMethod) = ("", null), cntnrStop: (String, HttpMethod) = ("", null))

class SettingsExtension(config: Config) extends Extension {
  val dockerInfoReq: String = config.getString("container.info.uri")

  def getDbCntnrCmds: CntnrCmds = {
    val config = ConfigFactory.load()

    val cntnr: String = config.getString("container.database.name")

    val cntnrCreate: (String, HttpMethod) =
      (config.getString(s"container.${cntnr}.create.uri"), POST)
    val cntnrStart: (String, HttpMethod) =
      (config.getString(s"container.${cntnr}.start.uri"), POST)
    val cntnrStop: (String, HttpMethod) =
      (config.getString(s"container.${cntnr}.stop.uri"), POST)
    val cntnrRemove: (String, HttpMethod) =
      (config.getString(s"container.${cntnr}.remove.uri"), POST)

    CntnrCmds(cntnr, cntnrCreate, cntnrRemove, cntnrStart, cntnrStop)
  }

  def getStorageCntnrCmds: CntnrCmds = {
    val config = ConfigFactory.load()

    val cntnr: String = config.getString("container.storage.name")

    val cntnrCreate: (String, HttpMethod) =
      (config.getString(s"container.${cntnr}.create.uri"), POST)
    val cntnrRemove: (String, HttpMethod) =
      (config.getString(s"container.${cntnr}.remove.uri"), POST)

    CntnrCmds(cntnr, cntnrCreate, cntnrRemove)
  }
}

object Settings extends ExtensionId[SettingsExtension] with ExtensionIdProvider {

  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) = {
    new SettingsExtension(system.settings.config)
  }
}
