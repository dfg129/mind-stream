package models.definitions

import models.{AddressId, Address}
import slick.driver.PostgresDriver.api._


class Addresses(tag: Tag) extends Table[Address](tag, "ADDRESSES") {
  def id = column[AddressId]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def domain = column[String]("DOMAIN")
  def * = (id, name, domain) <> (Address.tupled, Address.unapply)
}
