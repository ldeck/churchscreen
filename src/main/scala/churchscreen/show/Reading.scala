package churchscreen.show

import java.awt.{Rectangle, Dimension, Point}

import scala.io.StdIn.readLine

object Reading
{
  def readPassage =
  {
    println("Enter passage:")
    readLine()
  }

  def readPageNumber =
  {
    println("Enter page number:")
    readLine()
  }

  def bibleReadingTitleAnchor = new Rectangle(new Point(51, 129), new Dimension(921, 260))

  def bibleReadingTextAnchor = new Rectangle(new Point(51, 396), new Dimension(921, 320))
}
