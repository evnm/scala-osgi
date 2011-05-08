package com.kiip.osgi

import java.io.{BufferedReader, InputStreamReader}
import java.util.{Properties => JProperties}
import org.osgi.framework.launch.{Framework => OSGiFramework, FrameworkFactory}

/**
 * A helper object that makes interfacing with OSGi frameworks
 * easier from Scala.
 * */
object Framework {
  /**
   * Starts a framework instance with the given properties.
   * */
  def start(config: JProperties = null) = {
    val fwk = factory().newFramework(config)
    fwk.init()
    fwk.start()
    fwk
  }

  /**
   * Gets the OSGi FrameworkFactory which can be used to initialize
   * a new OSGi framework.
   * */
  def factory(): FrameworkFactory = {
    getClass().getClassLoader().getResource("META-INF/services/org.osgi.framework.launch.FrameworkFactory") match {
      case null => throw new FrameworkFactoryNotFound
      case url  =>
        val reader = new BufferedReader(new InputStreamReader(url.openStream()))
        val lines = Iterator.continually(reader.readLine()).takeWhile(_ != null).map(_.trim())

        // Find first non-comment non-empty line and attempt to instantiate
        // the framework factory.
        lines.find(line => line.length > 0 && !line.startsWith("#")) match {
          case Some(clazz) => Class.forName(clazz).newInstance().asInstanceOf[FrameworkFactory]
          case None        => throw new FrameworkFactoryClassNameNotFound
        }
    }
  }
}

/**
 * Enrichment class for the OSGi framework class. This makes interfacing
 * with an actual instance of Framework a bit easier.
 * */
class RichFramework(framework: OSGiFramework) {
  val context = framework.getBundleContext()
}
