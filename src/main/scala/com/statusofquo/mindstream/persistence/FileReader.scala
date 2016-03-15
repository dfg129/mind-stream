package com.statusofquo.mindstream.persistence
package utils


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import java.io.File
import akka.util.ByteString



object FileReader {

  implicit val system = ActorSystem("mindstream")
  import system.dispatcher
  implicit val materializer = ActorMaterializer()


  def checkFile = {
    val file = new File("TestFile.txt")
    FileIO.fromFile(file).runWith(Sink.foreach(println))
  }
}
