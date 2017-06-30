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
    print("Enter option [w]elcome, [l]ast, [s]ong(s), [r]eading, [t]ext file, [i]mport or [e]nd: ")

    val reader = new BufferedReader(new InputStreamReader(System.in))

    result = reader.readLine.trim match
    {
      case "w" => promptForWelcome match {
        case Nil => promptForAnotherSlide()
        case _ => true
      }
      case "l" => promptForLast match
      {
        case Nil => promptForAnotherSlide()
        case _ => true
      }
      case "s" => promptForSongs match
      {
        case Nil => promptForAnotherSlide()
        case _ => true
      }
      case "r" => promptForReading match
      {
        case Nil => promptForAnotherSlide()
        case _ => true
      }
      case "t" => promptForTextFile match
      {
        case Nil => promptForAnotherSlide()
        case _ => true
      }
      case "i" => promptForSlideShowToImportFrom match
      {
        case Nil => promptForAnotherSlide()
        case _ => true
      }
      case "e" => false
      case _ => promptForAnotherSlide()
    }
    result
  }

  private def promptForWelcome : List[Slide] =
  {
    List(show.create(Slide.welcome))
  }

  private def promptForLast : List[Slide] =
  {
    List(show.create(Slide.last))
  }

  private def promptForReading : List[Slide] =
  {
    List(show.create(Slide.reading))
  }

  private def promptForTextFile : List[Slide] =
  {
    List.empty
  }

  private def printSongList() : Unit =
  {
    val songs : Map[Int, String] = SortedMap(Song.names.view.zipWithIndex map {case (name, index) => (index, name)} : _*)

    songs foreach {
      case (index, name) => println("[%02d] %s".format(index, name))
    }
    hasPrintedSongList = true
  }

  private def promptForSlideShowToImportFrom : List[Slide] =
  {
    print("Enter path to slideshow to import: ")
    readLine() match {
      case "" => Nil
      case path => ImportedShow(show, path).slides()
    }
  }

  private def promptForSongs : List[Song] =
  {
    val songs : Map[Int, String] = SortedMap(Song.names.view.zipWithIndex map {case (name, index) => (index, name)} : _*)

    if (!hasPrintedSongList)
    {
      printSongList()
    }

    songIndexChoices(validOptions = songs.keySet) match {
      case Nil => Nil
      case is => is.map(i => Song(show, songs(i)))
    }
  }

  private def songIndexChoices(validOptions : Set[Int]) : List[Int] =
  {
    print("Enter song number(s) or [c]ancel: ")
    readLine().trim match {
      case "c" => Nil
      case i if i.matches("\\d+(\\s?,\\s?\\d+)*") &&
        validOptions.&~(i.split("\\s?,\\s?").map(_.toInt).toSet).nonEmpty =>
        i.split("\\s?,\\s?").map(_.toInt).toList
      case _ => songIndexChoices(validOptions)
    }
  }
}
