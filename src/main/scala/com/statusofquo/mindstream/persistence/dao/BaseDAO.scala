package com.statusofquo.mindstream.persistence
package dao


import models.definitions.{ UsersTable, AddressesTable, EmailsTable, DreamsPostsTable }
import slick.dbio.{Effect, NoStream}
import slick.lifted.TableQuery
import slick.profile.{FixedSqlAction, SqlAction, FixedSqlStreamingAction}
import util.DatabaseConfig
import scala.concurrent.Future
import scala.language.implicitConversions


trait BaseDAO extends DatabaseConfig {

  val usersTable        = TableQuery[UsersTable]
  val addressesTable    = TableQuery[AddressesTable]
  val emailsTable       = TableQuery[EmailsTable]
  val dreamsPostsTable  = TableQuery[DreamsPostsTable]


  protected implicit def executeFromDb[A](action: SqlAction[A, NoStream, _ <: slick.dbio.Effect]): Future[A] = {
    db.run(action)
  }

  protected implicit def executeReadStreamFromDb[A](action: FixedSqlStreamingAction[Seq[A], A, _ <: slick.dbio.Effect]): Future[Seq[A]] = {
    db.run(action)
  }
}
