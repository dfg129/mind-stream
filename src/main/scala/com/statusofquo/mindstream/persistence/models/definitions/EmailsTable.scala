package models.definitions

import models.{EmailId, Email, AddressId}
import slick.driver.PostgresDriver.api._

class EmailsTable(tag: Tag) extends Table[Email](tag, "EMAILS") {
  def id = column[EmailId]("ID", O.PrimaryKey, O.AutoInc)
  def fromAddress = column[AddressId]("FROM")
  def toAddress = column[AddressId]("TO")
  def subject = column[String]("SUBJECT")
  def body = column[String]("BODY")
  def state = column[String]("STATE")
  def * = (id, fromAddress, toAddress, subject, body, state) <> ((Email.apply _).tupled, Email.unapply)

  def fromAddressFK = foreignKey("FROM_FK", fromAddress, TableQuery[AddressesTable])(_.id)
  def toAddressFK = foreignKey("TO_FK", toAddress, TableQuery[AddressesTable])(_.id)
}
