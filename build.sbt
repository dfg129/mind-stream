
name := "mindstream"

shellPrompt := { state => s"[${name.value}] >> "}

lazy val commonSettings = Seq(
  organization := "com.statusofquo",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

lazy val akkaVersion = "2.4.2"

lazy val testRun = taskKey[Unit]("My file test task")

fullRunTask(testRun, Test, "com.statusofquo.mindstream.persistence.util.TestRun")

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "Mind@Stream"
  )

libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "com.typesafe.slick" %% "slick" % "3.1.1",
      "org.postgresql"     %  "postgresql"    % "9.4-1201-jdbc41",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "org.scalactic" %% "scalactic" % "2.2.6",
      "org.scalatest" %% "scalatest" % "2.2.6" % Test,
      "io.spray" %%  "spray-json" % "1.3.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
      "ch.qos.logback" % "logback-classic" % "1.1.2"
)



shellPrompt := { state =>
  def textColor(color: Int)             = { s"\033[38;5;${color}m" }
  def backgroundColor(color:Int) = { s"\033[48;5;${color}m" }
  def reset                                      = { s"\033[0m" }

  def formatText(str: String)(txtColor: Int, backColor: Int) = {
    s"${textColor(txtColor)}${backgroundColor(backColor)}${str}${reset}"
  }
  val red    = 1
  val green  = 2
  val yellow = 11
  val white  = 15
  val black  = 16
  val orange = 166

  formatText(s"[${name.value}]")(black, green) +
   "\n " +
   formatText("\u276f")(green, black) +
  formatText("\u276f")(yellow, black) +
  formatText("\u276f")(red, black)
}
