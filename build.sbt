name := "jsonHandler"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++=  Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.scala-lang" % "scala-parser-combinators" % "2.11.0-M4"
)
