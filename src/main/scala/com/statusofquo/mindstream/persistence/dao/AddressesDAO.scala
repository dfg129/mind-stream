package dao


import models.{AddressId, Address}
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future


object AddressesDAO extends BaseDAO {
    def findAll: Future[Seq[Address]] = addressesTable.result
    def create(address: Address): Future[AddressId] = addressesTable returning addressesTable.map(_.id) += address
    def delete(addressId: AddressId): Future[Int] = addressesTable.filter(_.id === addressId).delete
}
