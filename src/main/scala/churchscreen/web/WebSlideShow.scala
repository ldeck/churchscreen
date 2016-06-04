package churchscreen.web

import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: ldeck
 * Date: 2/09/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
object WebSlideShow
{
  def apply(file : File) : WebSlideShow =
  {
    new WebSlideShow(file = file)
  }
}

class WebSlideShow(val file : File)
{
  def create(options : ((WebSlide) => Unit)*) : WebSlide =
  {
    WebSlide(this, options:_*)
  }
}
