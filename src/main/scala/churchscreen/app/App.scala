package churchscreen.app

import churchscreen.show.{Slide,SlideShow}
import churchscreen.shell.{DateFile,User}
import churchscreen.web.{WebSlideShow, WebSlide}

object App extends HeadlessApp
{
  def main(args: Array[String])
  {
    val pptFile = DateFile(Constants.overheadsDir, "pptx")
    val webFile = DateFile(Constants.webDir, pptFile.fileName, "html")

    new App(pptFile, webFile)
  }
}

class App(val pptFile : DateFile, val webFile : DateFile)
{
  val pptShow = SlideShow(file = pptFile.file)
  val webShow = WebSlideShow(file = webFile.file)

  pptShow.create(Slide.welcome)
  webShow.create(WebSlide.welcome)

  pptShow.create(Slide.blank)
  webShow.create(WebSlide.blank)

  val user = User(pptShow, webShow)
  while (user.promptForAnotherSlide())
  {
    pptShow.create(Slide.blank)
    webShow.create(WebSlide.blank)
  }

  pptShow.create(Slide.last)
  webShow.create(WebSlide.last)

  pptShow.save()
//  webShow.save()
}
