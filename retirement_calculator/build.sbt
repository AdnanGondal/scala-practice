name := "retirement_calculator"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.3.0"

// enables compiler flag required by library to infer types
scalacOptions += "-Ypartial-unification"



mainClass in Compile := Some("retcalc.SimulatePlanApp")

