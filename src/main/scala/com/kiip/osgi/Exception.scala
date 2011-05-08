package com.kiip.osgi

/**
 * Exception thrown when the framework factory could not be found.
 * This is thrown when the required resource file for the OSGi spec
 * can not be found.
 * */
class FrameworkFactoryNotFound extends RuntimeException("FrameworkFactory resource not found.")

/**
 * Exception thrown when the framework factory class name could
 * not be found in the resource file for the framework factory.
 * */
class FrameworkFactoryClassNameNotFound extends RuntimeException("FrameworkFactory class name not found in resource.")
