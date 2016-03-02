package com.statusofquo.container
package docker.util


import akka.actor.{Actor, ActorSystem, ActorRef, Props}
import akka.event.Logging
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings

class ContainerInfoActor(next: ActorRef) extends Actor {

  import akka.pattern.pipe
  import context.dispatcher

  val actorSystem = ActorSystem("docker")

  val log = Logging(actorSystem, this)


  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(actorSystem))

  val http = Http(actorSystem)

 override def preStart() = {
    log.debug("#######  In the preStart")

    http.singleRequest(HttpRequest(uri = "http://akka.io"))
     .pipeTo(self)
   }

  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      log.debug("### Got response, body: " + entity.dataBytes.runFold(ByteString(""))(_ ++ _))
    case HttpResponse(code, _, _, _) =>
      log.debug("### Request failed, response code: " + code)
    case msg => {
        log.debug("############  inside " + msg + " #### " + next)
        next ! msg
      }
    }
}

object ContainerInfoActor {
  def props(next: ActorRef): Props = Props(new ContainerInfoActor(next))
}
