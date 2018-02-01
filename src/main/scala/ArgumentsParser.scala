/**
  * Created by Pietro.Speri on 31/01/2018.
  */
trait ArgumentsParser {

  def usage = println("\nThe arguments passed are incorrect. Please refer to the following usage example.\n" +
    "USAGE EXAMPLE: jsonHandler (arg1)[FILE] [OPTION: -a, -b, -c]")

  type ParserMap = Map[Symbol, String]

  def argsParser(map : ParserMap, l: List[String]) : ParserMap = {
    def withPar(opt : String) = (opt(0)=='-')
    l match {
      case Nil => map
      case "-a" :: value :: t => argsParser(map ++ Map('parA -> value), t)
      case "-b" :: value :: t => argsParser(map ++ Map('parB -> value), t)
      case "-c" :: value :: t => argsParser(map ++ Map('parC -> value), t)
      case s :: opt2 :: _ if withPar(opt2) => argsParser(map ++ Map('par -> s), l.tail)
      case _ => {
        println("\nINCORRECT COMMAND: " + l.reduce(_+" "+_))
        usage
        None
      }
    }
  }

}
