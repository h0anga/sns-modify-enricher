name := "sns-modify-enricher"

version := "0.1.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "2.1.0"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.apache.kafka" % "kafka-streams-test-utils" % "2.1.0" % Test

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging"  % "3.5.0"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)
dockerBaseImage := "openjdk:8-jre-alpine"

mainClass in Compile := Some("sns.lando.modify.enhancer.ModifyEnhancerApp")