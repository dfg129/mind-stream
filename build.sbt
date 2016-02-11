
lazy val commonSettings = Seq(
  organization := "com.statusofquo",
  version := "0.1.0",
  scalaVersion := "2.11.6"
)

lazy val akkaVersion = "2.4.2-RC2"


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
      "com.typesafe.slick" %% "slick" % "3.1.1",
      "org.postgresql"     %  "postgresql"    % "9.4-1201-jdbc41",
      "org.slf4j" % "slf4j-nop" % "1.6.4"
)
