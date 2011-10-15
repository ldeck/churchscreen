package myscreen

import java.io.File
import java.util.Calendar
import java.text.SimpleDateFormat

/**
 * $Revision:$
 * $Date:$
 * $Author:$
 */

object DateFile
{
	def apply(dir : String) : DateFile =
	{
		val fileDate = promptForDate
		val fileName = "%s.ppt".format(fileDate)
		new DateFile(dir, fileName)
	}

	private def promptForDate : String =
	{
		val defaultAnswer = nextSunday
		print("Enter the service date [%s]:".format(defaultAnswer))
		readLine match
		{
			case s if (s.matches("\\d{4}-\\d{2}-\\d{2}")) => s
			case d if ("".equals(d)) => defaultAnswer
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

class DateFile(val dir : String, val fileName : String)
{
	def file : File =
	{
		new File(dir, fileName)
	}

	def path = file.getAbsolutePath
}
