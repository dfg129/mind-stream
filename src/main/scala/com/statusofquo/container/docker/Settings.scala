package com.statusofquo.container
package docker

import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import akka.actor.ExtendedActorSystem
import akka.http.scaladsl.model.HttpMethod
import akka.http.scaladsl.model.HttpMethods._
import com.typesafe.config.{ Config, ConfigFactory }


case class HttpReq(uri: String, method: HttpMethod)

class SettingsExtension(config: Config) extends Extension {
  val dockerInfoReq: String = config.getString("container.info.uri")
}

object Settings extends ExtensionId[SettingsExtension] with ExtensionIdProvider {

  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) = {
    new SettingsExtension(system.settings.config)
  }

  val config = ConfigFactory.load()
  val dbcntnr = "database" //config.getString("container.database.name")
  val storagecntnr = "storage" //config.getString("container.storage.name")

  val dbCntnrCreate = HttpReq(config.getString(s"container.${dbcntnr}.create.uri"), POST)

  val dbCntnrStart = HttpReq(config.getString(s"container.${dbcntnr}.start.uri"), POST)

  val dbCntnrStop = HttpReq(config.getString(s"container.${dbcntnr}.stop.uri"), POST)

  val dbCntnrRemove = HttpReq(config.getString(s"container.${dbcntnr}.remove.uri"), POST)

  val storageCntnrCreate = HttpReq(config.getString(s"container.${storagecntnr}.create.uri"), POST)

  val storageCntnrRemove = HttpReq(config.getString(s"container.${storagecntnr}.remove.uri"), POST)

}
