package com.statusofquo.container
package docker

import akka.actor.{ Actor, ActorSystem }
import akka.event.Logging
import akka.testkit.{ TestKit, TestActorRef, ImplicitSender }
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }
import akka.stream.ActorMaterializer
import scala.concurrent.Await
import akka.http.scaladsl.model.HttpMethods._
import com.typesafe.scalalogging._

class ContainerInfoSpec() extends TestKit(ActorSystem())
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with ScalaFutures
  with LazyLogging
  with BeforeAndAfterAll {


val testRef = system.actorOf(ContainerInfoActor.props(testActor))

   "A test" should {
       "run test1" in {
         info("####    testing 1")
       }
   }

 "A docker info actor" should {
    "return return docker machine info" in {
      val actorRef = TestActorRef(ContainerInfoActor.props(testActor))

      DockerRemoteComm.init
      val settings = Settings(system)
      DockerRemoteComm.sendDockerRequest(actorRef, settings.dockerInfoReq, GET)
    }
  }

  "A docker container start actor" should {
   "return status" in {
     val actorRef = TestActorRef(ContainerInfoActor.props(testActor))

     DockerRemoteComm.init
     val settings = Settings(system)
     DockerRemoteComm.sendDockerRequest(actorRef, settings.dbCntnrStart._1, settings.dbCntnrStart._2)
   }
 }
}
