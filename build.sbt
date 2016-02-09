
lazy val commonSettings = Seq(
  organization := "com.statusofquo",
  version := "0.1.0",
  scalaVersion := "2.11.6"
)

lazy val akkaVersion = "2.4.2-RC2"


lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "sigchimes"
  )

libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion
)
