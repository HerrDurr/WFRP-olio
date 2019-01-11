name := "WFRPOlio"
version := "0.1.1"
scalaVersion := "2.11.8"
mainClass in Compile := Some("StartOliofx")

//resolvers += Resolver.bintrayRepo("elderresearch", "OSS")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)


libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.3", // Typesafe Config
//  "com.elderresearch" %% "ssc" % "1.0.0", // Simple Scala Config
  "com.typesafe.slick" %% "slick" % "3.2.3", // Typesafe Slick
  "org.slf4j" % "slf4j-nop" % "1.7.25",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3" exclude("org.slf4j", "slf4j-nop"),
  "org.scalafx" %% "scalafx" % "8.0.181-R13", // Scalafx
  "org.xerial" % "sqlite-jdbc" % "3.18.0", // SQLite JDBC
  "com.chuusai" %% "shapeless" % "2.3.3", // Shapeless
  "io.getquill" %% "quill-jdbc" % "2.6.0", // Quill
  "org.scala-lang.modules" %% "scala-swing" % "2.0.0", // Scala Swing
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.4" // ScalaFXML
)