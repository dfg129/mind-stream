package com.statusofquo.container
package docker.util


import akka.actor.{Actor, ActorSystem}
import akka.event.Logging
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings

class ContainerInfo extends Actor {

  import akka.pattern.pipe
  import context.dispatcher

  val log = Logging(context.system, this)
  val actorSystem = ActorSystem("docker")

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(actorSystem))

  val http = Http(actorSystem)

  override def preStart() = {
    log.info("In the preStart")
    http.singleRequest(HttpRequest(uri = "http://akka.io"))
      .pipeTo(self)
  }

  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      log.info("### Got response, body: " + entity.dataBytes.runFold(ByteString(""))(_ ++ _))
    case HttpResponse(code, _, _, _) =>
      log.info("### Request failed, response code: " + code)
    case _ => println("############  inside ")
  }
}
