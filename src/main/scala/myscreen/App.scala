package myscreen

import java.io.{File}

/**
 * 
 */
object App extends HeadlessApp
{
	def main(args: Array[String])
	{
		if (args.length == 1)
		{
			new App(args.head)
		}
		else if (args.length == 2 && args(0).equals("-d") && new File(args(1)).isDirectory)
		{
			val dateFile = DateFile(args(1))
			println("Will save file: %s".format(dateFile.path))
			new App(dateFile.path)
		}
		else
		{
			println("Usage: [-d] <pptFileToSave>")
		}
	}
}

class App(val pathToSave : String)
{
	val show = SlideShow(filePath = pathToSave)

	show.create(Slide.welcome)
	show.create(Slide.blank)

	val user = User(show)
	while (user.promptForAnotherSlide)
	{
		show.create(Slide.blank)
	}

	show.create(Slide.last)
	show.save
}
