/**
  * Created by Pietro.Speri on 31/01/2018.
  */
trait ArgumentsParser {

  def usage = println("USAGE EXAMPLE: jsonHandler (arg1)[FILE] [OPTION: -a, -b, -c]")

  type ParserMap = Map[Symbol, Any]
  @throws
  def argsParser(map : ParserMap, l: List[String]) : Option[ParserMap] = {
    def withPar(opt : String) = (opt(0)=='-')
    l match {
      case Nil => Some(map)
      case "-a" :: value :: t => argsParser(map ++ Map('parA -> value), t)
      case "-b" :: value :: t => argsParser(map ++ Map('parB -> value), t)
      case "-c" :: value :: t => argsParser(map ++ Map('parC -> value), t)
      case s :: opt2 :: t if withPar(opt2) => argsParser(map ++ Map('par -> s), l.tail)
      case s :: Nil =>  argsParser(map ++ Map('infile -> s), l.tail)
      case op :: t => {
        usage
        System.exit(1)
        argsParser(Map('Unknown->op),t.tail)
      }
    }
  }

}
