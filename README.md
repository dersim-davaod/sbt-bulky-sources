![plugin version](https://img.shields.io/static/v1?label=version&message=0.1.0-SNAPSHOT&color=brightgreen)

[![Build Status](https://travis-ci.com/dersim-davaod/sbt-bulky-sources.svg?branch=main)](https://travis-ci.com/dersim-davaod/sbt-bulky-sources)
![sbt version](https://img.shields.io/static/v1?label=sbt&message=1.4.7&color=brightgreen)
![Scala version](https://img.shields.io/static/v1?label=scala&message=2.13.4&color=brightgreen&logo=scala)

# Bulky sources plugin for SBT

## Overview

An [sbt](https://www.scala-sbt.org) (Simple Build Tool) plugin that provides statistics about the large-size files in the sbt console.
Its purpose is to provide a Bird's-eye view of your project in terms of the size of source files.
Files that are quite large or "bulky" are displayed, so the developer can treat that as a sort of warning.
It is recommended to make a decision to separate implementation into smaller files then.

## Installation (currently only local SNAPSHOT version)

1. First, [install sbt 1.4.7 or higher](https://www.scala-sbt.org/release/docs/Setup.html).
2. Clone the current plugin to the local machine.
3. Navigate into the plugin's root dir and do a local publishing:

```
sbt publishLocal
```

4. Then, on the same machine, add the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("dersimdavaod.scalabootcamp" % "sbt-bulky-sources" % "0.1.0-SNAPSHOT")
```

5. Optionally, run [tests](#testing) to validate the current implementation of the plugin.

## Using the plugin

The sbt-bulky-sources plugin adds the `bulkySources` task to `sbt`, and is meant to be run from the `sbt` console.

To analyze your project and see a list of bulky files, run the following command from sbt console:
```
> show bulkySources [threshold]
```

where the `threshold` defines how many lines the file should include at least to be treated as a bulky.

The default threshold value is 100 lines. The default value is applied if the user's input is not an unsigned integer.

### Examples:

#### Running on main sources with the default threshold

```
> show bulkySources
[info] * (430, .../src/main/scala/../../../A.scala)
[info] * (130, .../src/main/scala/../../../B.scala)
[info] * (100, .../src/main/scala/../../../C.scala)
```

#### Running on main sources with the custom threshold

```
> show bulkySources 300
[info] * (430, .../src/main/scala/../../../A.scala)
```

#### Running on test sources with the default threshold

```
> sbt show test:bulkySources 300
[info] * (500, .../src/test/scala/../../../A.scala)
```

## Testing

The plugin uses [`sbt` scripted tests](https://www.scala-sbt.org/1.x/docs/Testing-sbt-plugins.html) framework for self-testing.

The current project includes [scripted-plugin](src/sbt-test/src-bulky-sources-plugin/smoke/test) that performs smoke testing.

To run the test, go back to the plugin project, and run the following command from `sbt` console:

```
> scripted
```

This will copy your test build into a temporary dir, and executes the test script.
If everything works out, you’d see publishLocal running, then the result of testing.

## License

Please see [the license file](LICENSE.md).

