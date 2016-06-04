package churchscreen.app

import churchscreen.show.{Slide,SlideShow}
import churchscreen.shell.{DateFile,User}
import churchscreen.web.{WebSlideShow, WebSlide}

object App extends HeadlessApp
{
  def main(args: Array[String])
  {
    val pptFile = DateFile(Constants.overheadsDir, "pptx")
    new App(pptFile)
  }
}

class App(val pptFile : DateFile)
{
  val pptShow = SlideShow(file = pptFile.file)

  pptShow.create(Slide.welcome)
  pptShow.create(Slide.blank)

  val user = User(pptShow)
  while (user.promptForAnotherSlide())
  {
    pptShow.create(Slide.blank)
  }

  pptShow.create(Slide.last)

  pptShow.save()
}
