package com.kiip.osgi

import org.osgi.framework.ServiceReference

class RichServiceReference(reference: ServiceReference) {
  val properties = {
    val pairs: Array[(String,Any)] = reference.getPropertyKeys() match {
      case null => Array.empty
      case keys => keys.map(key => (key, reference.getProperty(key)))
    }

    Map(pairs: _*)
  }
}
