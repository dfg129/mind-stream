package com.statusofquo.mindstream.persistence
package dao

import models.{PostId, DreamsPost}
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future

object DreamsPostsDAO extends BaseDAO {
    def findAll: Future[Seq[DreamsPost]] = dreamsPostsTable.result
    def create(post: DreamsPost): Future[PostId] = dreamsPostsTable returning dreamsPostsTable.map(_.id) += post
    def delete(postId: PostId): Future[Int] = dreamsPostsTable.filter(_.id === postId).delete
}
