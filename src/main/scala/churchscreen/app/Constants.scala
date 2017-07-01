package churchscreen.app

import java.io.{BufferedReader, File, FileReader}

object Constants
{
  def baseDir = new File(System.getProperty("base.dir", System.getProperty("user.home")), "churchscreen")
  private val outDir = System.getProperty("out.dir")

  def ccliLicenceNo: String = {
    val no = System.getProperty("ccli.no")
    if (no != null) {
      no
    } else {
      val preferenceFile = new File(Constants.userHomeDir, ".churchscreen/ccli_no")
      var userDefined: String = null
      if (preferenceFile.canRead && preferenceFile.length() != 0) {
        userDefined = new BufferedReader(new FileReader(preferenceFile)).readLine()
      }
      if (userDefined != null && !userDefined.trim.isEmpty) userDefined
      else "CCLI"
    }
  }

  def rawTextDir = new File(Constants.baseDir, "rawtext")

  def overheadsDir: File = if (outDir != null) new File(outDir) else new File(Constants.baseDir, "overheads")

  def backgroundsDir = new File(Constants.baseDir, "backgrounds")

  def userHomeDir = new File(System.getProperty("user.home"))

  def webDir = new File(Constants.baseDir, "web")
}
