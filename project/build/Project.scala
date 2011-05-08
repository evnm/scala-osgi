import sbt._

class ScalaOSGiProject(info: ProjectInfo) extends DefaultProject(info) {
  val osgiCore = "org.osgi" % "org.osgi.core" % "4.2.0" % "provided"
  val osgiCompendium = "org.osgi" % "org.osgi.compendium" % "4.2.0" % "provided"
}
