package churchscreen.show

import org.apache.poi.hslf.usermodel.{SlideShow => ISlideShow}
import java.awt.Dimension
import java.io.{FileOutputStream, File}

object SlideShow
{
  def apply(file : File, pageSize : Dimension = new Dimension(1024, 768)) : SlideShow =
  {
    val slideShow = new ISlideShow
    slideShow.setPageSize(pageSize)

    new SlideShow(show = slideShow, file = file)
  }
}

class SlideShow(val show : ISlideShow, val file : File)
{
  def create(options : ((Slide) => Unit)*) : Slide =
  {
    Slide(this, show.createSlide, options:_*)
  }

  def save() : Unit =
  {
    val out = new FileOutputStream(file)
    show.write(out)
    out.close()
  }
}
