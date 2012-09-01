package churchscreen.show

import churchscreen.app.{Constants}
import java.io.{FileNotFoundException, File}
import java.awt.{Dimension, Point, Rectangle, Color}
import org.apache.poi.hslf.usermodel.RichTextRun
import org.apache.poi.hslf.model.{TextShape, TextBox, Fill, Slide => HSlide, Picture}

object Slide
{
  def apply(show : SlideShow, slide : HSlide, options : ((Slide) => Unit)*) : Slide =
  {
    val theSlide = new Slide(show, slide)
    options.foreach(_.apply(theSlide))
    return theSlide
  }

  def bibleReading = (s:Slide) =>
  {
    val p = new Point(7, 505)
    val d = new Dimension(236, 257)
    s.picSlide(pngName = "bible.png", r = new Rectangle(p, d))

    print("Enter passage:")
    val passage = readLine

    print("Enter page number:")
    val pageNumber = readLine

    s.addTitle(title = "Bible Reading", anchor = bibleReadingTitleAnchor, align = TextShape.AlignCenter, fontName = "Bank Gothic", fontSize = 96, color = Color.BLACK)

    val text = "%s\np%s".format(passage, pageNumber)
    s.addText(text = text, anchor = bibleReadingTextAnchor, align = TextShape.AlignCenter, fontName = "Arial Rounded MT Bold", fontSize = 43, color = Color.DARK_GRAY)
  }

  private def bibleReadingTitleAnchor = new Rectangle(new Point(51, 129), new Dimension(921, 260))

  private def bibleReadingTextAnchor = new Rectangle(new Point(51, 396), new Dimension(921, 320))

  def blank = (s:Slide) =>
  {
    s.blank(Color.BLACK)
  }

  def last = (s:Slide) =>
  {
    s.blank(Color.WHITE)
    s.picSlide("last.png")
  }

  def welcome = (s:Slide) =>
  {
    s.blank(Color.WHITE)
    s.picSlide("first.png")

    print("Enter welcome text:")
    val seriesText = readLine
    s.addText(text = seriesText, anchor = welcomeTextAnchor, fontSize = 22, color = Color.ORANGE, align = TextShape.AlignRight)
  }

  private def welcomeTextAnchor = new Rectangle(new Point(7, 512), new Dimension(572, 33))
}

class Slide(val show : SlideShow, val slide : HSlide)
{
  private def defaultTextAnchor = new Rectangle(new Point(71, 77), new Dimension(881, 591))
  private def defaultTitleAnchor = new Rectangle(new Point(71, 26), new Dimension(881, 49))

  def addTitle(title:String, anchor:Rectangle = defaultTitleAnchor, align:Int = TextShape.AlignLeft, fontName:String = "Gill Sans", fontSize:Int = 24, color:Color = Color.DARK_GRAY, bold:Boolean = true, valign:Int = TextShape.AnchorBottom, shadow:Boolean = true) : Unit =
    {
      val titleBox : TextBox = slide.addTitle
      alterTextBox(titleBox, text = title, lineSpacing = 100, anchor = anchor, align = align, fontName = fontName, fontSize = fontSize, color = color, bold = bold, valign = valign, shadow = shadow)
    }

  def addText(text:String, lineSpacing:Int = 0, anchor:Rectangle = defaultTextAnchor, align:Int = TextShape.AlignLeft, fontName:String = "Gill Sans", fontSize:Int = 48, color:Color = Color.BLACK, bold:Boolean = false, valign:Int = TextShape.AnchorTop, shadow:Boolean = false) : Unit =
  {
    val textBox : TextBox = new TextBox
    alterTextBox(textBox, text = text, lineSpacing = lineSpacing, anchor = anchor, fontName = fontName, fontSize = fontSize, color = color, bold = bold, align = align, valign = valign, shadow = shadow)
    slide.addShape(textBox)
  }

  private def alterTextBox(
    box : TextBox,
    text : String,
    lineSpacing : Int,
    anchor : Rectangle,
    align : Int = TextShape.AlignCenter,
    fontName : String,
    fontSize : Int,
    color : Color = Color.BLACK,
    bold : Boolean = false,
    valign : Int = TextShape.AnchorTop,
    shadow : Boolean = false
  )
    : Unit =
  {
    val run: RichTextRun = box.createTextRun.getRichTextRuns.head

    box.setAnchor(anchor)
    box.setVerticalAlignment(valign)

    run.setAlignment(align)
    run.setBold(bold)
    run.setFontName(fontName)
    run.setFontSize(fontSize)
    run.setFontColor(color)
    run.setShadowed(shadow)
    run.setSpaceAfter(lineSpacing)

    box.setText(text)
  }

  protected[Slide] def blank(color : Color) : Unit =
  {
    slide.setFollowMasterBackground(false)

    val fill: Fill = slide.getBackground.getFill
    fill.setBackgroundColor(color)
    fill.setForegroundColor(color)
    fill.setFillType(Fill.FILL_SOLID)
  }

  protected[Slide] def picSlide(pngName : String) : Unit =
  {
    val show = slide.getSlideShow
    val pageSize = show.getPageSize

    picSlide(pngName = pngName, r = new Rectangle(new Point(0, 0), pageSize))
  }

  protected[Slide] def picSlide(pngName : String, r : Rectangle) : Unit =
  {
    val show = slide.getSlideShow
    val png = picFile(pngName)

    val idx = show.addPicture(png, Picture.PNG)
    val picture = new Picture(idx)
    picture.setAnchor(r)

    slide.addShape(picture)
  }

  private[Slide] def picFile(name : String) : File =
  {
    val png = new File(Constants.backgroundsDir, name)
    if (!png.isFile)
    {
      throw new FileNotFoundException("No file named %s in %s".format(name, Constants.backgroundsDir.getAbsolutePath))
    }
    return png
  }
}
