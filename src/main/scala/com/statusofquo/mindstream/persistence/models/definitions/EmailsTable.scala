package models.definitions

import models.{EmailId, Email}
import slick.driver.PostgresDriver.api._

class Emails(tag: Tag) extends Table[Email](tag, "EMAILS") {
  def id = column[EmailId]("ID", O.PrimaryKey, O.AutoInc)
  def fromAddress = column[AddressId]("FROM")
  def toAddress = column[AddressId]("TO")
  def subject = column[String]("SUBJECT")
  def body = column[String]("BODY")
  def state = column[String]("STATE")
  def * = (id, from, to, subject, body, state) <> (Email.apply_).tupled, Email.unapply)

  def fromAddress = foreignKey("FROM_FK", fromAddress, TableQuery[AddressesTable])(_.id)
  def toAddress = foreignKey("TO_FK", toAddress, TableQuery[AddressesTable])(_.id)
}
