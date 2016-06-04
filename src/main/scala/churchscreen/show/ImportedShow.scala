package churchscreen.show

import churchscreen.app.Constants
import java.io.{FileInputStream, File}
import org.apache.poi.hslf.usermodel.{SlideShow => ISlideShow}
import org.apache.poi.hslf.model.{Slide => ISlide}
import org.apache.poi.hslf.model.{Shape => Shape}

object ImportedShow
{
  def apply(currentShow : SlideShow, path : String) : ImportedShow =
  {
    val importedShow = new ISlideShow(new FileInputStream(expandedPath(path)))
    importedShow.setPageSize(currentShow.show.getPageSize)

    println("importing slides...")
    importedShow.getSlides.foreach { slide => copySlide(slide, currentShow) }
    
    new ImportedShow(currentShow, importedShow)
  }

  private def copySlide(slide : ISlide, toShow : SlideShow)
  {
    val dup : Slide = toShow.create()
    slide.getShapes.foreach { shape => copyShape(shape = shape, toSlide = dup) }
  }

  private def copyShape(shape : Shape, toSlide : Slide)
  {
    toSlide.hslide.addShape(shape)
  }

  private def expandedPath(path : String) : File =
  {
    val seq_path : Seq[Char] = path
    seq_path match {
      case Seq('~', '/', rest @ _*) => new File(Constants.userHomeDir, rest.toString())
      case _ => new File(path)
    }
  }
}

class ImportedShow(val show : SlideShow, val importedShow : ISlideShow)
