name := "segen"

version := "1.0"

scalaVersion := "2.11.4"

mainClass in (Compile, run) := Some("seng.GameApp")

javaOptions in run += "-Djava.library.path=lib/natives"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

//resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  "com.scalarx" %% "scalarx" % "0.2.6",
  "org.scala-lang" % "scala-reflect" % "2.11.4",
  "org.scala-lang" % "scala-compiler" % "2.11.4",
  "com.badlogicgames.gdx" % "gdx" % "1.5.3",
  "com.badlogicgames.gdx" % "gdx-platform" % "1.5.3" classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.5.3",
  "org.spockframework" % "spock-core" % "0.7-groovy-2.0" % "test",
  "org.specs2" %% "specs2-core" % "2.4.13" % "test",
  "org.specs2" %% "specs2-mock" % "2.4.13" % "test"
)

unmanagedBase := baseDirectory.value / "lib"

fork in run := true
