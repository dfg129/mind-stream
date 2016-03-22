package com.statusofquo.mindstream.persistence
package utils

import org.scalatest._
import org.scalatest.{FlatSpec, Matchers}
import scala.util.{ Success, Failure }
import scala.concurrent.ExecutionContext.Implicits.global


class UtilsSpecs  extends FlatSpec with Matchers {


 "A file" should "be read" in {
   FileReader.foreach.onComplete {
     case Success(msg) => info("!!!!!!!!!!!!!!  " + msg.status.get)
     case Failure(_) => info("+++++++++++++++")
   }
 }

}
