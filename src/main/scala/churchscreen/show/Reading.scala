package churchscreen.show

object Reading
{
  def apply(show : SlideShow) : Reading =
  {
    new Reading(show, show.create(Slide.bibleReading))
  }
}

class Reading(val show : SlideShow, val slide : Slide)
