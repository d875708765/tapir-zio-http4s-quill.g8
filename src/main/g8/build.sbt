import Dependencies._

ThisBuild / scalaVersion := "2.13.7"

// for cats Kleisli
addCompilerPlugin(
  "org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full
)

scalacOptions ++= Seq(
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-Ymacro-annotations", // cirece @JsonCodec
  "-Vimplicits",
  "-Vtype-diffs",
  "-Wunused"
)

lazy val commonSettings = Seq(
  name := "tapir-zio-http4s-quill",
  scalacOptions ++= Seq(
    "-feature",
    "-language:experimental.macros",
    "-Xlint:-multiarg-infix",
    "-Xmaxwarns",
    "1024",
    "-Xmaxerrs",
    "1024",
    "-Ymacro-annotations",
    "-Vimplicits",
    "-Vtype-diffs"
  ),
  //  scalacOptions ++= Seq("-language:experimental.macros", "--verbose", "-Xlog-implicits"),
  addCompilerPlugin(
    "org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.full
  ),
  libraryDependencies ++= coreDependency,
  scalacOptions ++= Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature" // Emit warning and location for usages of features that should be imported explicitly.
  )
)

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin, DockerPlugin, JavaAppPackaging, JavaAgent)
  .settings(commonSettings)
  .settings(dockerSettings)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "buildInfo.build"
  )

val dockerSettings = Seq(
  dockerBaseImage := "openjdk:17-bullseye",
  dockerUsername := None,
  dockerExposedPorts := Seq(9000)
)
