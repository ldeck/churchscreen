/*
 * Created by IntelliJ IDEA.
 * User: ldeck
 * Date: 30/04/11
 * Time: 11:02 PM
 */
package myscreen

import org.apache.poi.hslf.usermodel.{SlideShow => ISlideShow}
import java.awt.Dimension
import java.io.{FileOutputStream, File}

object SlideShow
{
	def apply(filePath : String, pageSize : Dimension = new Dimension(1024, 768)) : SlideShow =
	{
		val slideShow = new ISlideShow
		slideShow.setPageSize(pageSize)

		new SlideShow(show = slideShow, file = new File(filePath))
	}
}

class SlideShow(val show : ISlideShow, val file : File)
{
	def create(options : ((Slide) => Unit)*) : Slide =
	{
		Slide(this, show.createSlide, options:_*)
	}

	def createBlankSlide() : Slide =
	{
		create(options = Slide.blank)
	}

	def createWelcomeSlide() : Slide =
	{
		create(options = Slide.welcome)
	}

	def save() : Unit =
	{
		val out = new FileOutputStream(file)
		show.write(out)
		out.close
	}
}
