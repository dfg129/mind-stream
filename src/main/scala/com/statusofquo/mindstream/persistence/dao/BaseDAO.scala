
package com.statusofquo.sigchimes.persistence.dao


import models.definitions.{UsersTable, AddressesTable, EmailsTable}
import slick.dbio.{Effect, NoStream}
import slick.lifted.TableQuery
import slick.profile.{FixedSqlAction, SqlAction, FixedSqlStreamAction}
import utils.DatabaseConfig
import scala.concurrent.Future




trait BaseDAO extends DatabaseConfig {

  val usersTable = TableQuery[UsersTable]
  val addressesTable = TableQuery[AddressesTable]
  val emailsTable = TableQuery[EmailsTable]

  protected implicit def executeFromDb[A](action: SqlAction[A, NoStream, _ <: slick.dbio.Effect]): Future[A] = {
    db.run(action)
  }

  protected implicit def executeReadStreamFromDb[A](action: FixedSqlStreamingAction[Seq[A], A, _ <: slick.dbio.Effect]): Future[A] = {
    db.run(action)
  }
}
