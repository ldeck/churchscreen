package churchscreen.web

import java.awt.Color

import org.apache.poi.hslf.model.TextShape

/**
 * Created with IntelliJ IDEA.
 * User: ldeck
 * Date: 2/09/12
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
object WebSlide
{
  def apply(show : WebSlideShow, options : ((WebSlide) => Unit)*) : WebSlide =
  {
    val webSlide = new WebSlide(show)
    options.foreach(_.apply(webSlide))
    return webSlide
  }

  def blank = (s:WebSlide) =>
  {

  }

  def last = (s:WebSlide) =>
  {

  }

  def welcome = (s:WebSlide) =>
  {

  }
}

class WebSlide(val show : WebSlideShow)
{

}
