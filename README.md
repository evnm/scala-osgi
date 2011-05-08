# Scala OSGi

`scala-osgi` is a library meant to ease the burden of interfacing with
OSGi from Scala.

## Why?

[OSGi](http://www.osgi.org/Main/HomePage) is a powerful and mature standard
for creating a module or plugin environment in JVM applications. Once it is
up and running, OSGi makes your life easier, not harder, but getting to that
point can be challenging since learning the initial concepts of OSGi are not
easy and the OSGi API itself is fairly low-level.

`scala-osgi` was built to be a library around OSGi using Scala's implicits.
As an alternative, [ScalaModules](https://github.com/weiglewilczek/scalamodules)
provides a great DSL for consuming and creating OSGi services.

## Quick Start

`scala-osgi` is a library built upon Scala's implicits in order to enrich
pre-existing OSGi classes. Therefore, instead of picking specific classes
to import, its recommend you import all, in order to bring the implicit
conversions into the scope:

    import com.kiip.osgi._

After this, you use OSGi classes, like normal, except `scala-osgi` provides
some helpers:

    // Initialize OSGi framework.
    val framework = Framework.start(Map("settings" -> "value"))

    // Get the BundleContext
    val context = framework.context

    // Install some bundles from a list of jars
    val jars = List("file:///foo/bar/baz.jar")
    context.installBundles(jars)

    // Start all installed bundles
    for (bundle <- context.bundles)
      bundle.start

    // Track a service
    context.trackServices[MyService] {
      case ServiceAdded(service, properties) => println("Service added!")
      case ServiceRemoved(service, properties) => println("Service removed!")
      case ServiceModified(service, properties) => println("Service modified!")
    }

## Building

If you'd like to build `scala-osgi` from source, check out the git repository
and run the following:

    sbt compile

You can also `sbt package`, `sbt publish-local`, or whatever you'd like,
since `scala-osgi` is an SBT project.

## Contributing

Find a bug? Have a feature request? File an [issue](https://github.com/kiip/scala-osgi/issues)
or submit a [pull request](https://github.com/kiip/scala-osgi/pulls)
and we'll work with you on merging it in. Don't
worry too much about style or doing it "the right way," since we'd
rather get more contributions than burdening you with making sure its
absolutely perfect.
