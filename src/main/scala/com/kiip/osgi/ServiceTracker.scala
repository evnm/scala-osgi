package com.kiip.osgi

import org.osgi.framework.{BundleContext, ServiceReference}
import org.osgi.util.tracker.ServiceTrackerCustomizer

/**
 * The parent event of all events that service trackers should
 * listen for.
 * */
sealed abstract class ServiceEvent[I](service: I, properties: Map[String,Any])

/**
 * Event that is sent when a service is added.
 * */
case class ServiceAdded[I](service: I, properties: ServiceProperties)
extends ServiceEvent(service, properties)

/**
 * Event that is sent when a service is modified.
 * */
case class ServiceModified[I](service: I, properties: ServiceProperties)
extends ServiceEvent(service, properties)

/**
 * Event that is sent when a service is removed.
 * */
case class ServiceRemoved[I](service: I, properties: ServiceProperties)
extends ServiceEvent(service, properties)

/**
 * This is a service tracker handler which will notify a callback handler
 * whenever an event occurs. This should be used by calling `trackServices`
 * on a bundle context rather than instantiating this directly.
 * */
private[osgi] class ServiceTrackerHandler[I](context: BundleContext,
                                             handler: PartialFunction[ServiceEvent[I],Unit])
                                             (implicit manifest: Manifest[I])
extends ServiceTrackerCustomizer {
  // Since this value is only used for debugging, mark it as
  // lazy so that its not loaded on instantiation.
  lazy val interface = manifest.erasure.asInstanceOf[Class[I]]

  override def addingService(reference: ServiceReference) = {
    val service = context.getService(reference)
    val event   = ServiceAdded(service.asInstanceOf[I], reference.properties)
    if (handler.isDefinedAt(event)) {
      handler(event)
    }

    service
  }

  override def modifiedService(reference: ServiceReference, service: AnyRef) {
    val event = ServiceModified(service.asInstanceOf[I], reference.properties)
    if (handler.isDefinedAt(event)) {
      handler(event)
    }
  }

  override def removedService(reference: ServiceReference, service: AnyRef) {
    val event = ServiceRemoved(service.asInstanceOf[I], reference.properties)
    if (handler.isDefinedAt(event)) {
      handler(event)
    }

    context.ungetService(reference)
  }
}
