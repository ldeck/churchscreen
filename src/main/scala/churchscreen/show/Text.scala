package churchscreen.show

import java.awt.{Color, Dimension, Point, Rectangle}
import java.io.File

import churchscreen.app.Constants
import org.apache.commons.io.FilenameUtils
import org.apache.poi.hslf.model.TextShape

import scala.io.Source

object Text
{

}

class Text(val show : SlideShow, val file : File)
{
  private def ccliPermissionText = "Used by permission. CCLI Licence No. " + Constants.ccliLicenceNo
  private def ccliPermissionAnchor = new Rectangle(new Point(71, 711), new Dimension(881, 26))

  private def footerAnchor = new Rectangle(new Point(71, 680), new Dimension(881, 26))
  private def footerAnchorDimensions = new Dimension(881, 26)
  private def footerAnchorPoint = new Point(71, 680)
  private def footerFontName = "Monaco"
  private def footerFontSize = 14

  private def lineSpacing = 85
  private val paragraphs = Source.fromFile(file, "UTF-8").mkString.split("(\r?\n){2}")

  def displayName = paragraphs.head
  def copyright = paragraphs.last
  def contentForSlides = paragraphs.dropRight(1)

  println("song %s has %d paragraphs".format(displayName, paragraphs.length))

  private def init() : Unit =
  {
    val titleSlide : Slide = show.create()
    titleSlide.addTitle(title = displayName)
    titleSlide.addText(text = contentForSlides.head, lineSpacing = lineSpacing)

    var slide : Slide = null
    for (text <- contentForSlides.tail)
    {
      slide = show.create()
      slide.addText(text = text, lineSpacing = lineSpacing)
    }

    // copyright
    slide.addText(
      text = copyright,
      anchor = rectForText(copyright, footerAnchor),
      align = TextShape.AlignRight,
      fontName = footerFontName,
      fontSize = footerFontSize,
      color = Color.GRAY,
      bold = false,
      valign = TextShape.AnchorBottom
    )

    // CCLI
    slide.addText(
      text = ccliPermissionText,
      anchor = ccliPermissionAnchor,
      align = TextShape.AlignRight,
      fontName = footerFontName,
      fontSize = footerFontSize,
      color = Color.GRAY,
      bold = false,
      valign = TextShape.AnchorBottom
    )
  }

  protected[Text] def rectForText(text: String, offset: Rectangle): Rectangle =
  {
    val point = offset.getLocation
    val dim = offset.getSize

    val count = text.lines.toSeq.size
    val newHeight = dim.height * count
    val newPoint = new Point(point.x, point.y - dim.height * (count - 1))
    val newDim = new Dimension(dim.width, newHeight)

    new Rectangle(newPoint, newDim)
  }

  init()
}
