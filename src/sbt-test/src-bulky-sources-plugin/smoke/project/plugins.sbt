// For more details,
// see https://www.scala-sbt.org/1.x/docs/Testing-sbt-plugins.html

sys.props.get("plugin.version") match {
  case Some(version) => addSbtPlugin("dersimdavaod.scalabootcamp" % "sbt-bulky-sources" % version)
  case _       => sys.error("""|The system property 'plugin.version' is not defined.
                               |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}
