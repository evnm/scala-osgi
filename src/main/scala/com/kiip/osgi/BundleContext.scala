package com.kiip.osgi

import java.io.File
import org.osgi.framework.{Bundle, BundleContext}
import org.osgi.util.tracker.ServiceTracker

class RichBundleContext(context: BundleContext) {
  def bundles: List[Bundle] = context.getBundles().toList

  /**
   * Install bundles from a list of files.
   * */
  def installBundles(files: List[File]) = files.foreach(installBundle(_))

  /**
   * Install a bundle from a file.
   * */
  def installBundle(file: File) = context.installBundle(file.toURI.toString)

  /**
   * Sets up a tracker for services which are registered on
   * this bundle context.
   * */
  def trackServices[I](handler: PartialFunction[ServiceEvent[I], Unit])(implicit manifest: Manifest[I]) = {
    val interface = manifest.erasure.asInstanceOf[Class[I]]
    val tracker = new ServiceTracker(context, interface.getName(),
                                     new ServiceTrackerHandler(context, handler))
    tracker.open
    tracker
  }
}
