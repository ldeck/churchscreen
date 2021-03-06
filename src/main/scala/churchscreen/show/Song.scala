package churchscreen.show

import churchscreen.app.Constants
import org.apache.commons.io.FilenameUtils

import io.Source
import java.io.{File, FilenameFilter}

import org.apache.poi.hslf.model.TextShape
import java.awt.{Color, Dimension, Point, Rectangle}

import scala.collection.mutable

object Song
{
  def apply(show : SlideShow, name : String) : Song =
  {
    apply(show = show, file = new File(Constants.rawTextDir, name + ".txt"))
  }

  def apply(show : SlideShow, file : File) : Song =
  {
    new Song(show = show, file = file)
  }

  def list : Seq[File] =
  {
    val filter = new FilenameFilter
    {
      override def accept(dir: File, name: String) = name.toLowerCase.endsWith(".txt")
    }
    Constants.rawTextDir.listFiles(filter).toSeq.sortWith(_.getName < _.getName)
  }

  def names : Seq[String] =
  {
    list map { f => FilenameUtils.getBaseName( f.getName ) }
  }
}

class Song(val show : SlideShow, val file : File)
{
  private def ccliPermissionText = "Used by permission. CCLI Licence No. " + Constants.ccliLicenceNo
  private def ccliPermissionAnchor = new Rectangle(new Point(71, 711), new Dimension(881, 26))

  private def titleFontName = bodyFontName
  private def titleFontSize = 36

  private def bodyFontName = "Capitals"
  private def bodyLineSpacing = 32

  private def footerAnchor = new Rectangle(new Point(71, 680), new Dimension(881, 26))
  private def footerAnchorDimensions = new Dimension(881, 26)
  private def footerAnchorPoint = new Point(71, 680)
  private def footerFontName = "Monaco"
  private def footerFontSize = 14

  private val paragraphs = Source.fromFile(file, "UTF-8").mkString.split("(\r?\n){2}")

  def copyright = paragraphs.last

  def contentForSlides = paragraphs.dropRight(1)

  def displayName = FilenameUtils.getBaseName(file.getName)

  println("song %s has %d paragraphs".format(displayName, paragraphs.length))

  private val _slides: mutable.MutableList[Slide] = mutable.MutableList()
  def slides() : List[Slide] = _slides.toList

  private def init() : Unit =
  {
    val titleSlide : Slide = show.create()
    titleSlide.addTitle(title = displayName, underline = true, fontName = titleFontName, fontSize = titleFontSize)
    addBodyTextToSlide(text = contentForSlides.head, slide = titleSlide)

    for (text <- contentForSlides.tail)
    {
      _slides += show.create()
      addBodyTextToSlide(text = text, slide = _slides.last)
    }

    // copyright
    _slides.last.addText(text = copyright, anchor = footerAnchor(copyright), align = TextShape.AlignRight, fontName = footerFontName, fontSize = footerFontSize, color = Color.GRAY, valign = TextShape.AnchorBottom)

    // CCLI
    _slides.last.addText(text = ccliPermissionText, anchor = ccliPermissionAnchor, align = TextShape.AlignRight, fontName = footerFontName, fontSize = footerFontSize, color = Color.GRAY, valign = TextShape.AnchorBottom)
  }

  private def addBodyTextToSlide(text: String, slide: Slide) : Unit = {
    slide.addText(text = text, fontName = bodyFontName, lineSpacing = bodyLineSpacing, bold = true)
  }

  private def footerAnchor(text : String) : Rectangle =
  {
    val point = footerAnchorPoint
    val dim = footerAnchorDimensions

    val count = text.lines.toSeq.size
    val newHeight = dim.height * count
    val newPoint = new Point(point.x, point.y - dim.height * (count - 1))
    val newDim = new Dimension(dim.width, newHeight)

    new Rectangle(newPoint, newDim)
  }

  init()
}
