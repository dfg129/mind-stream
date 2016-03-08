package com.statusofquo.container
package docker

import akka.actor.{ Actor, ActorSystem, ActorRef, Props }
import akka.http.scaladsl.{ Http, HttpsConnectionContext }
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings
import akka.event.Logging
import scala.util.{ Try, Success, Failure }
import com.typesafe.config.ConfigFactory



import java.io._
import java.security._
import javax.net.ssl._


class DockerRemoteComm(implicit val system: ActorSystem) {
  import akka.pattern.pipe
  import system.dispatcher

  val http = Http()
}

object DockerRemoteComm {
  implicit val system = ActorSystem("docker")
  val comm = new DockerRemoteComm

  def init = {
    comm.http.setDefaultClientHttpsContext(createHttpsContext)
  }

  def sendDockerRequest(actor: ActorRef, uri: String) = {
    implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))

    import akka.pattern.pipe
    import system.dispatcher

    comm.http.singleRequest(HttpRequest(uri = uri))
    .pipeTo(actor)
  }

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
