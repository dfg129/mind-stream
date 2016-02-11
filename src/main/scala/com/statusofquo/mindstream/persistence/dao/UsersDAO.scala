package dao


import models.{UserId, User}
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future


object UsersDAO extends BaseDAO {
    def findAll: Future[Seq[User]] = usersTable.result
    def create(user: User): Future[UserId] = usersTable returning usersTable.map(_.id) += user
    def delete(userId: UserId): Future[Int] = usersTable.filter(_.id === userId).delete
}
