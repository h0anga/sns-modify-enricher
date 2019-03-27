name := "sns-modify-enricher"

version := "0.1.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "2.1.0"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.apache.kafka" % "kafka-streams-test-utils" % "2.1.0" % Test

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging"  % "3.5.0"

libraryDependencies ++= Seq(
"io.zipkin.brave" % "brave-instrumentation-kafka-clients" % "5.6.3",
"io.zipkin.brave" % "brave-instrumentation-kafka-streams" % "5.6.3",
"io.zipkin.reporter2" % "zipkin-sender-kafka11" % "2.8.1"
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)
dockerBaseImage := "openjdk:8-jre-alpine"

mainClass in Compile := Some("sns.lando.modify.enhancer.ModifyEnhancerApp")