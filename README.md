[![Build Status](https://travis-ci.com/dersim-davaod/sbt-bulky-sources-plugin.svg?branch=main)](https://travis-ci.com/dersim-davaod/sbt-bulky-sources-plugin)
![sbt version](https://img.shields.io/static/v1?label=sbt&message=1.4.6&color=brightgreen)
![Scala version](https://img.shields.io/static/v1?label=scala&message=2.13.4&color=brightgreen&logo=scala)
![JDK version](https://img.shields.io/static/v1?label=JDK&message=15.0.2&color=brightgreen&logo=java)

# Bulky sources plugin for SBT (PUBLIC BETA)

## Overview

An [sbt](https://www.scala-sbt.org) (Simple Build Tool) plugin
that provides statistics about the size of source files in the sbt console.
Its purpose is to provide a Bird's-eye view of your project in terms of the size of source files.
Those files that are large or "bulky" are displayed, so the developer can treat that as a sort of warning.

## Installation (currently PUBLIC BETA or SNAPSHOT version)

1. First, [install sbt 1.4.7 or higher](https://www.scala-sbt.org/release/docs/Setup.html).
1. Clone the current plugin to the local machine.
1. Navigate into the plugin's root dir and do a local publishing:

```
sbt publishLocal
```

1. Then, on the same machine, add the following to your project/plugins.sbt file:

```scala
addSbtPlugin("dersimdavaod.scalabootcamp" % "sbt-bulky-sources" % "0.1.0-SNAPSHOT")
```

and enable plugin in the `build.sbt` file:
```
enablePlugins(BulkySourcesPlugin)
```

## Usage sbt-bulky-sources plugin

The sbt-bulky-sources plugin adds the `bulkySources` task to sbt, and is meant to be run from the sbt console.

To see the list of files that are "large", run the following command from sbt console:
```
show bulkySources [threshold]
```

where the `threshold` defines how many lines the file should include at least to be treated as a "large" or "bulky".

### Examples:

#### Running with the default threshold value

```
show bulkySources
```

Output:

```
[info] * (430, .../src/main/scala/../../../A.scala)
[info] * (130, .../src/main/scala/../../../B.scala)
[info] * (100, .../src/main/scala/../../../C.scala)
```

#### Running with the custom threshold value

```
show bulkySources 300
```

Output:

```
[info] * (430, .../src/main/scala/../../../A.scala)
```

#### Running on your test sources

```
sbt show test:bulkySources 300
```

Output:

```
[info] * (500, .../src/test/scala/../../../A.scala)
```

## Testing

To test the plugin you can use sbt scripted tests framework,
which lets you script a build scenario.

The current projects includes [scripted-plugin](src/sbt-test/src-bulky-sources-plugin/smoke/test) that performs smoke tests.

To run the scripts, go back to the plugin project, and run from sbt console:

```
> scripted
```

This will copy your test build into a temporary dir, and executes the test script.
If everything works out, you’d see publishLocal running, then the result of testing.

## License

Please see [the license file](LICENSE.md).
