name := "retirement_calculator"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

mainClass in Compile := Some("retcalc.SimulatePlanApp")