/*
 * Created by IntelliJ IDEA.
 * User: ldeck
 * Date: 3/05/11
 * Time: 5:24 AM
 */
package myscreen


object Reading
{
	def apply(show : SlideShow) : Reading =
	{
		new Reading(show, show.create(Slide.bibleReading))
	}
}

class Reading(val show : SlideShow, val slide : Slide)
