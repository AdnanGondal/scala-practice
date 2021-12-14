name := "EmailAPI"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies += "com.github.daddykotex" %% "courier" % "3.0.1"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.7"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,

  "com.typesafe.akka" %% "akka-http-testkit"        % AkkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion     % Test,
  "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test
)

