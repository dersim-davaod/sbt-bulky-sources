package dersimdavaod.scalabootcamp.bulkysourcesplugin

import sbt._
import sbt.complete._
import sbt.complete.DefaultParsers.{NatBasic, Space, token}

object BulkySources {
  def apply(sourceFiles: Seq[File], threshold: Int): Seq[(Int, File)] = sourceFiles
    .map(file => (IO.readLines(file).length, file))
    .filter { case (linesInFile, _) => linesInFile >= threshold }
    .sorted
    .reverse
  
  object Defaults {
    val thresholdInLines: Int = 100
  }

  val thresholdTokenParser: Def.Initialize[Parser[Int]] =
    Def.setting {
      Space ~> token(NatBasic, "<threshold value>") ?? Defaults.thresholdInLines
    }
} 
