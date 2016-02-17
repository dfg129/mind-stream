package com.statusofquo.mindstream.persistence
package dao

import org.scalatest._
import scala.concurrent.Future

import models.DreamsPost

class DreamsPostsUnitTest extends UnitTest("DreamsPostsDAO") {

  "A DreamsPost" should "be present using findAll" in {
    val postDAO = DreamsPostsDAO();
    val result: Future[Seq[DreamsPost]] = postDAO.findAll();

    assert(result != null)
    val dp = for {
      posts <- result
      post <- posts head
    } yield ( post )

    assert(dp != null)
  }
}
