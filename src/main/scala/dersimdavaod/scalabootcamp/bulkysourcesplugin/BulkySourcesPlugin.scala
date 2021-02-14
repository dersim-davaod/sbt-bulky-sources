package dersimdavaod.scalabootcamp.bulkysourcesplugin

import sbt._
import sbt.Keys._

object BulkySourcesPlugin extends AutoPlugin {
  
  object autoImport {
    lazy val bulkyThresholdInLines = settingKey[Int]("threshold to check if the particular source is bulky. The default value is 100.")
    lazy val bulkySources = inputKey[Seq[(Int, File)]]("lists large or bulky sources for which the number of lines it contains equals to or more than a given threshold.")
  }
  
  import autoImport._

  override lazy val globalSettings: Seq[Setting[_]] = Seq(
    bulkyThresholdInLines := BulkySources.Defaults.thresholdInLines,
  )

  override def projectSettings =
    inConfig(Compile)(baseBulkySourcesSettings) ++
    inConfig(Test)(baseBulkySourcesSettings)

  private lazy val baseBulkySourcesSettings: Seq[Setting[_]] = Seq(
    bulkySources := Def.inputTaskDyn {
      val threshold = BulkySources.thresholdTokenParser.parsed

      Def.task { 
        BulkySources(sources.value, threshold),
      }
    }.evaluated
  )

  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = Plugins.empty
}

