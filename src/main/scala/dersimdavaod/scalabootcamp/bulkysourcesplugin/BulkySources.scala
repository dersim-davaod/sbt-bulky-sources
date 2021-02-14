package dersimdavaod.scalabootcamp.bulkysourcesplugin

import sbt._
import sbt.Keys._
import sbt.complete._
import sbt.complete.DefaultParsers.{NatBasic, Space, token}

object BulkySources extends AutoPlugin {
  
  object autoImport {
    lazy val bulkyThresholdInLines = SettingKey[Int]("threshold to check if the particular source is bulky. The default value is 100.")
    lazy val bulkySources = inputKey[Seq[(Int, File)]]("List large or bulky sources for which the number of lines it contains equals to or more than a given threshold.")
  }
  
  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    (Test / bulkySources) := listBulkySourcesTask(Test).evaluated,
    (Compile / bulkySources) := listBulkySourcesTask(Compile).evaluated,
    bulkyThresholdInLines := 100
  )

  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = Plugins.empty
  
  private val thresholdTokenParser: Def.Initialize[Parser[Int]] =
    Def.setting {
      Space ~> token(NatBasic, "<threshold>") ?? bulkyThresholdInLines.value
    }

  private def listBulkySourcesTask(configuration: Configuration): Def.Initialize[InputTask[Seq[(Int, File)]]] =
    Def.inputTaskDyn {
      val threshold = thresholdTokenParser.parsed
      val files = (configuration / sources).value

      Def.task {
        files
          .map(file => (IO.readLines(file).size + 1, file))
          .filter { case (linesInFile, _) => linesInFile >= threshold }
          .sorted
          .reverse
      }
    }
}
