package dersimdavaod.scalabootcamp.bulkysourcesplugin

import sbt._
import sbt.Keys._
import sbt.complete._
import sbt.complete.DefaultParsers.{NatBasic, Space, token}

object BulkySourcesPlugin extends AutoPlugin {
  
  object autoImport {
    lazy val bulkyThresholdInLines = settingKey[Int]("threshold to check if the particular source is bulky. The default value is 100.")
    lazy val bulkySources = inputKey[Seq[(Int, File)]]("list large or bulky sources for which the number of lines it contains equals to or more than a given threshold.")
  }
  
  import autoImport._

  override lazy val globalSettings: Seq[Setting[_]] = Seq(
    bulkyThresholdInLines := BulkySources.Defaults.thresholdInLines,
  )

  override def projectSettings: Seq[Setting[_]] = Seq(
    (Test / bulkySources) := BulkySources.listBulkySourcesTask(Test).evaluated,
    (Compile / bulkySources) := BulkySources.listBulkySourcesTask(Compile).evaluated,
  )

  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = Plugins.empty
}

object BulkySources {
  object Defaults {
    val thresholdInLines: Int = 100
  }

  val thresholdTokenParser: Def.Initialize[Parser[Int]] =
    Def.setting {
      Space ~> token(NatBasic, "<threshold value>") ?? Defaults.thresholdInLines
    }
   
  def listBulkySourcesTask(configuration: Configuration): Def.Initialize[InputTask[Seq[(Int, File)]]] =
    Def.inputTaskDyn {
      val threshold = BulkySources.thresholdTokenParser.parsed
      val files = (configuration / sources).value

      Def.task {
        files
          .map(file => (IO.readLines(file).length, file))
          .filter { case (linesInFile, _) => linesInFile >= threshold }
          .sorted
          .reverse
      }
    }
} 
