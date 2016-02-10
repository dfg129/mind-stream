
package com.statusofquo.sigchimes.persistence.dao

import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Address(id: Long, name: String, domain: String)
case class Email(id: Long, from: Long, to: Long, subject: String, body: String, state: String)

object MailDAO {

  val db = Database.forConfig("postgres")

  class Addresses(tag: Tag) extends Table[Address](tag, "ADDRESSES") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def domain = column[String]("DOMAIN")
    def * = (id, name, domain) <> (Address.tupled, Address.unapply)
  }

  val addresses = TableQuery[Addresses]

  class Emails(tag: Tag) extends Table[Email](tag, "EMAILS") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def from = column[Long]("FROM")
    def to = column[Long]("TO")
    def subject = column[String]("SUBJECT")
    def body = column[String]("BODY")
    def state = column[String]("STATE")
    def * = (id, from, to, subject, body, state) <> (Email.tupled, Email.unapply)

    def fromAddress = foreignKey("FROM_FK", from, addresses)(_.id)
    def toAddress = foreignKey("TO_FK", to, addresses)(_.id)
  }
  val emails = TableQuery[Emails]
}
