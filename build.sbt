name := "toll-plaza-system"
version := "1.0"
scalaVersion := "2.13.6"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.6.16",
  "com.typesafe.akka" %% "akka-stream-kafka" % "2.1.0",
  "org.slf4j" % "slf4j-simple" % "1.7.32",
  "io.spray" %% "spray-json" % "1.3.6"
)
