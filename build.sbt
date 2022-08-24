ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "UnboostedEndpointScript"
  )

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client3" %% "core" % "3.5.0",
  "io.github.etspaceman" % "scalacheck-faker_2.13" % "7.0.0",
  "com.storm-enroute" %% "scalameter-core" % "0.21"
)