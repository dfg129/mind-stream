package com.statusofquo.mindstream.persistence
package utils


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent.Future
import java.io._
import akka.util.ByteString



object FileReader {
  import akka.stream.IOResult
  implicit val system = ActorSystem("mindstream")
  implicit val materializer = ActorMaterializer()



  def checkFile = {
    val file = new File("test.txt")
    val foreach: Future[IOResult] = FileIO.fromFile(file)
      .to(Sink.ignore)
      .run()
  }

  def parseFile = ???

  def insertIntoDb = ???
}
