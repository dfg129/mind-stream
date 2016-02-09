import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.IOException
import scala.concurrent.{ExecutionContextExecutor, Future}


object RestHandler extends App {
 implicit val system = ActorSystem()
 implicit val executor = system.dispatcher
 implicit val materializer = ActorMaterializer()

 val routes = {
      path("test") {
        get {
          complete {
               HttpResponse(entity="<h1> here and there and everywhere </h1>")
             }
          }
        }
      }

 val config = ConfigFactory.load()
 val logger = Logging(system, getClass)

 Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

}
