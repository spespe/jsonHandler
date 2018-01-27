import java.util.Calendar
import com.typesafe.scalalogging.{LazyLogging, Logger}

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object jsonHandler extends LazyLogging {
  val usage = println("USAGE EXAMPLE: jsonHandler (arg1)[FILE] [OPTION: -a, -b, -c]")

  def main(args:Array[String]){
    println(Calendar.getInstance.getTime)
    logger.info("DATETIME")

    type ParserMap = Map[Symbol, Any]
    @throws
    def parse(map : ParserMap, l: List[String]) : Option[ParserMap] = {
      def withPar(opt : String) = (opt(0)=='-')
      l match {
        case Nil => Some(map)
        case "-a" :: value :: t => parse(map ++ Map('parA -> value), t)
        case "-b" :: value :: t => parse(map ++ Map('parB -> value), t)
        case "-c" :: value :: t => parse(map ++ Map('parC -> value), t)
        case string :: opt2 :: t if withPar(opt2) => parse(map ++ Map('par -> string), l.tail)
        case string :: Nil =>  parse(map ++ Map('infile -> string), l.tail)
        case op :: t => None
      }
    }
    logger.info("CHECKING INPUT ARGUMENTS")
    val argsList = args.toList
    val check = parse(Map(),argsList)
    println(check.get)

  }
}

