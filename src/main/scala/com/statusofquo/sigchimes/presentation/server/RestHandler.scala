import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.htto.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.IOException
import scala.concurrent.ExecutionContextExecutor, Future}
import scala.math._


val routes = {
  logRequestResult("rest-handler") {
    path
  }
}


object RestHandler extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

}
