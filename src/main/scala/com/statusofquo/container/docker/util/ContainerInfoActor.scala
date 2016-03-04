package com.statusofquo.container
package docker.util


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

class ContainerInfoActor(next: ActorRef) extends Actor {

  import akka.pattern.pipe
  import context.dispatcher

  val actorSystem = ActorSystem("docker")

  val log = Logging(actorSystem, this)


  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(actorSystem))

  val http = Http(actorSystem)
  http.setDefaultClientHttpsContext(ContainerInfo.createHttpsContext)

 override def preStart() = {
    log.debug("#######  In the preStart")

    http.singleRequest(HttpRequest(uri = "https://192.168.99.101:2376/info"))
     .pipeTo(self)
   }

  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      log.debug("### Got response, body: " + entity.dataBytes.runFold(ByteString(""))(_ ++ _))
    case HttpResponse(code, _, _, _) =>
      log.debug("### Request failed, response code: " + code)
    case msg: Status.Failure => {
      log.debug("--------- received message of type {}", msg.cause.printStackTrace());

      }
        log.debug("@@@@@@@@@@@@@@@@@@@@@@@")
    }

}

object ContainerInfo {
  def props(next: ActorRef): Props = Props(new ContainerInfoActor(next))

  def createHttpsContext: HttpsConnectionContext = {
    //TODO  - remove password from code
    val password = "dfg129".toCharArray

    val keyStore = KeyStore.getInstance("PKCS12")
    val context: HttpsConnectionContext = Try(loadCert(keyStore, password)) match {
      case Success(_) => {
          val keyManagerFactory = KeyManagerFactory.getInstance("SunX509")
          keyManagerFactory.init(keyStore, password)

          val context = SSLContext.getInstance("TLS")
          context.init(keyManagerFactory.getKeyManagers, null, new SecureRandom)

          new HttpsConnectionContext(context)
        }
      case Failure(e) => {
          e.printStackTrace
          throw e
        }
      }
     context
    }

  def loadCert(keyStore: KeyStore, password: Array[Char]) = {
    val stream = resourceStream("cert.p12")
    keyStore.load(stream, password)
  }

  def resourceStream(resource: String): InputStream = {
    val is = getClass.getClassLoader.getResourceAsStream(resource)
    require(is ne null, s"Resource $resource not found")
    is
  }
}
