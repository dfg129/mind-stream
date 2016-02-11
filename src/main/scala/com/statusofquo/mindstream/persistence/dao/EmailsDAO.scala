package dao


import models.{EmailId, Email}
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future


object EmailsDAO extends BaseDAO {
    def findAll: Future[Seq[Email]] = emailsTable.result
    def create(email: Email): Future[EmailId] = emailsTable returning emailsTable.map(_.id) += email
    def delete(emailId: EmailId): Future[Int] = emailsTable.filter(_.id === emailId).delete
}
