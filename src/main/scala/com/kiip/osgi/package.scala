package com.kiip

import java.util.{Properties => JProperties}
import org.osgi.framework.{BundleContext, ServiceReference}
import org.osgi.framework.launch.{Framework => OSGiFramework}

package object osgi {
  /**
   * Type representing properties of services and bundles.
   * */
  type Properties = JProperties

  /**
   * Type representing service properties.
   * */
  type ServiceProperties = Map[String,Any]

  /**
   * An implicit to easily convert an OSGi Framework instance
   * to an enriched version which provides helper methods.
   * */
  implicit def framework2RichFramework(fwk: OSGiFramework) =
    new RichFramework(fwk)

  /**
   * An implicit to easily convert an OSGi BundleContext to
   * an enriched version which provides easier interfacing from
   * Scala.
   * */
  implicit def bundleContext2RichBundleContext(bundleContext: BundleContext) =
    new RichBundleContext(bundleContext)

  /**
   * An implicit to easily convert an OSGi ServiceReference to
   * an enriched version.
   * */
  implicit def serviceRef2RichServiceRef(reference: ServiceReference) =
    new RichServiceReference(reference)

  /**
   * An implicit to easily convert a `Map[String,String]` to java
   * `Properties`, since properties are used all over OSGi but
   * isn't actually very canonical Scala usage.
   * */
  implicit def map2Properties(map: Map[String,String]): Properties = {
    val prop = new Properties()
    map.foreach(pair => prop.setProperty(pair._1, pair._2))
    prop
  }
}
