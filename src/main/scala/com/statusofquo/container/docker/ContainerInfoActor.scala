package com.statusofquo.container


import akka.actor.{Actor, ActorSystem, ActorRef, Props, Status }
import akka.event.Logging
import akka.util.ByteString
import akka.http.scaladsl.{ Http, HttpsConnectionContext }
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings
import scala.util.{ Try, Success, Failure }
import java.io._
import java.security._
import javax.net.ssl._
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ContainerInfoActor(next: ActorRef) extends Actor {

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val timeout = 2.seconds
  val log = Logging(context.system, this)

  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      val bs: Future[ByteString] = entity.toStrict(timeout).map { _.data }
      val s: Future[String] = bs.map(_.utf8String)
      s onSuccess {
        case json =>  //log.debug(json)
        next ! json
      }
      s onFailure {
        case msg => log.debug("Rsponse reports failure")
        next ! msg
      }
    case HttpResponse(code, _, _, _) =>
      log.error("Request failed, response code: " + code)
      next ! "### Request failed, response code: " + code
    case msg: Status.Failure => {
      log.error("received message of type {}", msg.cause.printStackTrace())
      next ! "Status failure, reason : " + msg.cause
    }
  }
}

object ContainerInfoActor {
  def props(next: ActorRef): Props = Props(new ContainerInfoActor(next))
}
