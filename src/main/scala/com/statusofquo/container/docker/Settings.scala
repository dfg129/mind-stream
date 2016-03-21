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

// persistent docker data volume settings
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

  sealed trait DockerReq 

  val config = ConfigFactory.load()

  val dbcntnr: String = config.getString("container.database.name")

  val dbCntnrCreate: (String, HttpMethod) =
    (config.getString(s"container.${dbcntnr}.create.uri"), POST)

  val dbCntnrStart: (String, HttpMethod) =
    (config.getString(s"container.${dbcntnr}.start.uri"), POST)

  val dbCntnrStop: (String, HttpMethod) =
    (config.getString(s"container.${dbcntnr}.stop.uri"), POST)

  val dbCntnrRemove: (String, HttpMethod) =
    (config.getString(s"container.${dbcntnr}.remove.uri"), POST)

}
