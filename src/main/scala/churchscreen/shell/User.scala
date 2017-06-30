package churchscreen.shell

import java.io.{BufferedReader, InputStreamReader}
import scala.collection.Set
import collection.immutable.SortedMap

import churchscreen.show._

import scala.io.StdIn.readLine

object User
{
  def apply(show : SlideShow) : User =
  {
    new User(show)
  }
}

class User(val show : SlideShow)
{
  private var hasPrintedSongList = false

  def promptForAnotherSlide() : Boolean =
  {
    var result = true
    print("Enter option [l]ist, [s]ong, [r]eading, [t]ext file, [i]mport or [e]nd: ")

    val reader = new BufferedReader(new InputStreamReader(System.in))

    result = reader.readLine.trim match
    {
      case "l" => printSongList(); promptForAnotherSlide()
      case "s" => promptForSong match
      {
        case None => promptForAnotherSlide()
        case _ => true
      }
      case "r" => promptForReading match
      {
        case None => promptForAnotherSlide()
        case _ => true
      }
      case "t" => promptForTextFile match
      {
        case None => promptForAnotherSlide()
        case _ => true
      }
      case "i" => promptForSlideShowToImportFrom match
      {
        case None => promptForAnotherSlide()
        case _ => true
      }
      case "e" => false
      case _ => promptForAnotherSlide()
    }
    result
  }

  private def promptForReading : Option[Slide] =
  {
    Some(show.create(Slide.reading))
  }

  private def promptForTextFile : Option[Boolean] =
  {
    Some(true)
  }

  private def printSongList() : Unit =
  {
    val songs : Map[Int, String] = SortedMap(Song.names.view.zipWithIndex map {case (name, index) => (index, name)} : _*)

    songs foreach {
      case (index, name) => println("[%02d] %s".format(index, name))
    }
    hasPrintedSongList = true
  }

  private def promptForSlideShowToImportFrom : Option[ImportedShow] =
  {
    print("Enter path to slideshow to import: ")
    readLine() match {
      case "" => None
      case path => Some(ImportedShow(show, path))
    }
  }

  private def promptForSong : Option[Song] =
  {
    val songs : Map[Int, String] = SortedMap(Song.names.view.zipWithIndex map {case (name, index) => (index, name)} : _*)

    if (!hasPrintedSongList)
    {
      printSongList()
    }

    songIndexChoice(validOptions = songs.keySet) match {
      case e if e < 0 => None
      case i => Some(Song(show, songs(i)))
    }
  }

  private def songIndexChoice(validOptions : Set[Int]) : Int =
  {
    print("Enter song number or [c]ancel: ")
    readLine() match {
      case "c" => -1
      case i if i.matches("\\d+") && validOptions.contains(i.toInt) => i.toInt
      case _ => songIndexChoice(validOptions)
    }
  }
}
