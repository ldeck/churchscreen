package churchscreen.app

import java.io.{File}

object Constants
{
  def baseDir = new File(System.getProperty("base.dir", System.getProperty("user.home")), "churchscreen")

  def rawTextDir = new File(Constants.baseDir, "rawtext")

  def overheadsDir = new File(Constants.baseDir, "overheads")

  def backgroundsDir = new File(Constants.baseDir, "backgrounds")
  
  def userHomeDir = new File(System.getProperty("user.home"))
}
