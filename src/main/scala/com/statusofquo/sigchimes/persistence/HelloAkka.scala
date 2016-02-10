
package com.statusofquo.sigchimes.persistence

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._
import slick.driver.PostgresDriver.api._


case object Greet
case class WhoToGreet(who: String)
case class Greeting(message: String)

class Greeter extends Actor {
  var greeting = ""

  def receive = {
    case WhoToGreet(who) => { println("accepted")
      greeting = s"hello, $who"
    }
    case Greet => sender ! Greeting(greeting)
  }
}

object HelloAkka  extends App {

  val db = Database.forConfig("postgres")

  class Suppliers(tag: Tag) extends Table[(Int, String, String)](tag, "SUPPLIERS") {
    def id = column[Int]("SUP_ID", O.PrimaryKey)
    def name = column[String]("SUP_NAME")
    def state = column[String]("STATE")
    def * = (id, name, state)
  }
  val suppliers = TableQuery[Suppliers]


  class Coffees(tag: Tag) extends Table[(String, Int, Double)](tag, "COFFEES"){
    def name = column[String]("COF_NAME", O.PrimaryKey)
    def supID = column[Int]("SUP_ID")
    def price = column[Double]("PRICE")
    def * = (name, supID, price)
    def supplier = foreignKey("SUP_FK", supID, suppliers)(_.id)
  }
  val coffees = TableQuery[Coffees]

  val setup = DBIO.seq(
    (suppliers.schema ++ coffees.schema).create,
    suppliers += (101, "Acme Inc", "CA"),
    suppliers += (49, "Superior Coffee", "HI"),
    suppliers += (150, "High Heat", "NY"),

    coffees ++= Seq(
      ("Columbian",   101, 7.99),
      ("French_Roast", 49, 8.99),
      ("Espresso", 150, 9.99)
    )
  )

 val setupFuture = db.run(setup)

/*  val system = ActorSystem("helloakka")

  val greeter = system.actorOf(Props[Greeter], "greeter")

  val inbox = Inbox.create(system)

  greeter.tell(WhoToGreet("akka"), ActorRef.noSender)

  inbox.send(greeter, Greet)

  val Greeting(message1) = inbox.receive(5.seconds)
  println(s"Greeting: $message1")

  greeter.tell(WhoToGreet("quo"), ActorRef.noSender)

  inbox.send(greeter, Greet)

  val Greeting(message2) = inbox.receive(5.seconds)
  println(s"Greeting: $message2")

  val greetPrinter = system.actorOf(Props[GreetPrinter])
  system.scheduler.schedule(0.seconds, 1.second, greeter, Greet)(system.dispatcher, greetPrinter)
*/
}

class GreetPrinter extends Actor {
  def receive = {
    case Greeting(message) => println(message)
  }
}
