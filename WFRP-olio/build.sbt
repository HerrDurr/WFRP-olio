name := "WFRPOlio"

val scalaVer = "2.11.8"

lazy val commonSettings = Seq(
  version := "0.1.1",
  scalaVersion := scalaVer,
  mainClass in Compile := Some("StartOliofx")
)



val sqliteJDBCVer = "3.18.0"
val quillVer = "2.6.0"

lazy val macroModule = project.in(file("macros"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect", // macros
      "org.scala-lang" % "scala-compiler" //macros
    ).map(_ % scalaVer),
    libraryDependencies ++= Seq(
      "org.xerial" % "sqlite-jdbc" % sqliteJDBCVer, // SQLite JDBC
      "io.getquill" %% "quill-jdbc" % quillVer // Quill
    )
  )

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.3", // Typesafe Config
      "com.typesafe.slick" %% "slick" % "3.2.3", // Typesafe Slick
      "org.slf4j" % "slf4j-nop" % "1.7.25",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3" exclude("org.slf4j", "slf4j-nop"),
      "org.scalafx" %% "scalafx" % "8.0.181-R13", // Scalafx
      "org.xerial" % "sqlite-jdbc" % sqliteJDBCVer, // SQLite JDBC
      "com.chuusai" %% "shapeless" % "2.3.3", // Shapeless
      "io.getquill" %% "quill-jdbc" % quillVer, // Quill
      "org.scala-lang.modules" %% "scala-swing" % "2.0.0", // Scala Swing
      "org.scalafx" %% "scalafxml-core-sfx8" % "0.4" // ScalaFXML
    ),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )
  .dependsOn(macroModule)