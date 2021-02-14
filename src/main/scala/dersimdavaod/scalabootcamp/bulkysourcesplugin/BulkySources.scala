package dersimdavaod.scalabootcamp.bulkysourcesplugin

import sbt._
import sbt.complete._
import sbt.complete.DefaultParsers.{NatBasic, Space, token}

/**
 * Implements core feature for the plugin.
 */
object BulkySources {
  def apply(sourceFiles: Seq[File], threshold: Int): Seq[(Int, File)] = sourceFiles
    .map(file => (IO.readLines(file).length, file))
    .filter { case (linesInFile, _) => linesInFile >= threshold }
    .sorted
    .reverse
  
  /**
   * Default values to use across the plugin.
   */
  object Defaults {
    /**
     * The minimum number of lines in a source file after which the file is treated as "bulky".
     */
    val thresholdInLines: Int = 100
  }

  /**
   * Parser that reads user arguments and parses threshold value.
   * In case of invalid value, the default threshold is applied.
   */
  val thresholdTokenParser: Def.Initialize[Parser[Int]] =
    Def.setting {
      Space ~> token(NatBasic, "<threshold value>") ?? Defaults.thresholdInLines
    }
} 
