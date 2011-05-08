import sbt._
import com.weiglewilczek.bnd4sbt.BNDPlugin

class ScalaOSGiProject(info: ProjectInfo) extends DefaultProject(info) with BNDPlugin {
  val osgiCore = "org.osgi" % "org.osgi.core" % "4.2.0" % "provided"
  val osgiCompendium = "org.osgi" % "org.osgi.compendium" % "4.2.0" % "provided"

  override def bndBundleSymbolicName = "com.kiip.osgi"
  override def bndBundleVendor = Some("Kiip")
  override def bndBundleLicense =
    Some("Apache 2.0 License (http://www.apache.org/licenses/LICENSE-2.0.html)")
  override def bndExportPackage = "com.kiip.osgi;version=\"%s\"".format(projectVersion.value) :: Nil
}
