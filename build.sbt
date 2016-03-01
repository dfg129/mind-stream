
lazy val commonSettings = Seq(
  organization := "com.statusofquo",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

lazy val akkaVersion = "2.4.2"


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
      "org.scalactic" %% "scalactic" % "2.2.6",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test",
      "io.spray" %%  "spray-json" % "1.3.2"
)
