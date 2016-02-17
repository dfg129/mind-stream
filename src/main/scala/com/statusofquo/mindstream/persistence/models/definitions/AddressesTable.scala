package com.statusofquo.mindstream.persistence
package models.definitions

import models.{AddressId, Address}
import slick.driver.PostgresDriver.api._


class AddressesTable(tag: Tag) extends Table[Address](tag, "ADDRESSES") {
  def id = column[AddressId]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def domain = column[String]("DOMAIN")
  def * = (id, name, domain) <> ((Address.apply _).tupled, Address.unapply)
}
