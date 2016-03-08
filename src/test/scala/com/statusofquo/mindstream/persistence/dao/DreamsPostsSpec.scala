package com.statusofquo.mindstream.persistence
package dao

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.PostgresDriver.api._
import org.scalatest.{FlatSpec, Matchers}

import models.definitions.DreamsPostsTable


import models.DreamsPost

class DreamsPostsSpec extends FlatSpec
with Matchers
with ScalaFutures {

 override def withFixture(test: NoArgTest) = {

   SetupTables.runPreset

    super.withFixture(test) match {
      case failed: Failed =>
        info("--------   this test failed " + failed)
        failed
      case other => other
    }
  }


  "A DreamsPost" should "be present using findAll" in {
    val f: Future[Seq[DreamsPost]] = DreamsPostsDAO findAll

    f onSuccess {
      case msg => println("############  success " + msg)
    }

    f onFailure {
      case msg => println("###########  failure " + msg)
    }

    whenReady(f) { result =>
      println(result)
      assert(result != null)
    }
  }
}


object SetupTables {

  val db = Database.forConfig("postgres")

  val posts = TableQuery[DreamsPostsTable]

  val setup = DBIO.seq(posts.schema.create)

  def runPreset = {
    val setupFuture = db.run(setup)

     setupFuture.onSuccess { case _ => println("DreamsPosts: good") }

     setupFuture onFailure { case e => println("DreamsPosts failed : " + e) }
  }
}
