ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / name := "sbt-bulky-sources"
ThisBuild / organization := "dersimdavaod.scalabootcamp"
ThisBuild / homepage := Some(url("https://github.com/dersim-davaod/sbt-bulky-sources"))

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false
  )
