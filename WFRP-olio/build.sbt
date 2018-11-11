name := "WFRPOlio"
version := "0.1.1"
scalaVersion := "2.11.8"
mainClass in Compile := Some("StartOliofx")

//resolvers += Resolver.bintrayRepo("elderresearch", "OSS")

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1", // Typesafe Config
//  "com.elderresearch" %% "ssc" % "1.0.0", // Simple Scala Config
  "com.typesafe.slick" %% "slick" % "3.2.3", // Typesafe Slick
  "org.slf4j" % "slf4j-nop" % "1.7.25",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3" exclude("org.slf4j", "slf4j-nop"),
  "org.scalafx" %% "scalafx" % "8.0.181-R13", // Scalafx
  "org.xerial" % "sqlite-jdbc" % "3.20.0", // SQLite JDBC
  "com.chuusai" %% "shapeless" % "2.3.3", // Shapeless
  "org.scala-lang.modules" %% "scala-swing" % "2.0.0" // Scala Swing
)