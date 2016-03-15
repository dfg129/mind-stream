package com.statusofquo.mindstream.persistence
package utils

import org.scalatest._
import org.scalatest.{FlatSpec, Matchers}


class UtilsSpecs  extends FlatSpec with Matchers {


 "A file" should "be read" in {
   FileReader.checkFile
 }

}
