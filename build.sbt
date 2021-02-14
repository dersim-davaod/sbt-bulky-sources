ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "dersimdavaod.scalabootcamp"
ThisBuild / homepage := Some(url("https://github.com/dersim-davaod/sbt-bulky-sources"))

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-bulky-sources",
    pluginCrossBuild / sbtVersion := {
      scalaBinaryVersion.value match {
        case "2.13" => "1.4.6"
      }
    },
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false
  )
