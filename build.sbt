name := "segen"

version := "1.0"

scalaVersion := "2.11.4"

//resolvers += "Scalaz Bintray Repo"  at "http://dl.bintray.com/scalaz/releases"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies += "com.scalarx" %% "scalarx" % "0.2.6"

libraryDependencies += "org.spockframework" % "spock-core" % "0.7-groovy-2.0"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.4"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.11.4"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "2.4.13" % "test",
  "org.specs2" %% "specs2-mock" % "2.4.13" % "test"
)


//libraryDependencies ++= Seq(
//  "org.lwjgl.lwjgl" % "lwjgl" % "2.9.1",
//  "org.lwjgl.lwjgl" % "lwjgl_util" % "2.9.1",
//  "org.lwjgl.lwjgl" % "lwjgl-platform" % "2.9.1"
//)

unmanagedBase := baseDirectory.value / "lib"
