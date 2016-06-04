package churchscreen.shell

import java.io.File
import java.util.Calendar
import java.text.SimpleDateFormat
import scala.io.StdIn.readLine

object DateFile
{
  def apply(dir : File, ext : String) : DateFile =
  {
    val fileDate = promptForDate
    new DateFile(dir, fileDate, ext)
  }

  def apply(dir : File, fileName : String, ext : String) : DateFile =
  {
    new DateFile(dir, fileName, ext)
  }

  private def promptForDate : String =
  {
    val defaultAnswer = nextSunday
    print("Enter the service date [%s]:".format(defaultAnswer))
    readLine() match
    {
      case s if s.matches("\\d{4}-\\d{2}-\\d{2}") => s
      case d if "".equals(d) => defaultAnswer
      case _ => promptForDate
    }
  }

  private def nextSunday : String =
  {
    val formatString = "yyyy-MM-dd"
    val cal = Calendar.getInstance
    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    if (dayOfWeek != Calendar.SUNDAY)
      {
        cal.add(Calendar.DAY_OF_WEEK, 1 + 7 - dayOfWeek)
      }
    new SimpleDateFormat(formatString) format cal.getTime
  }
}

class DateFile(val dir : File, val fileName : String, val fileExtension : String)
{
  def file : File =
  {
    val dateName = "%s.%s".format(fileName, fileExtension)
    new File(dir, dateName)
  }

  def path = file.getAbsolutePath
}
