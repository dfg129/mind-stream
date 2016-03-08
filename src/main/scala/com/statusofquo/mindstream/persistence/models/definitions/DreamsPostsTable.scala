package com.statusofquo.mindstream.persistence
package models.definitions

import models.{PostId, DreamsPost}
import slick.driver.PostgresDriver.api._

class DreamsPostsTable(tag: Tag) extends Table[DreamsPost](tag, "Dreams_Posts") {
  def id = column[PostId]("ID", O.PrimaryKey, O.AutoInc)
  def label = column[String]("FROM")
  def body = column[String]("BODY")
  def state = column[String]("STATE")
  def * = (id, label, body, state) <> ((DreamsPost.apply _).tupled, DreamsPost.unapply)
}
