package jsonHandler

/**
  * Created by Pietro.Speri on 31/01/2018.
  */

trait ArgumentsParser {

  type ParserMap = Map[Symbol, String]

  def usage = println("\nThe arguments passed are incorrect. Please refer to the following usage example.\n" +
    "USAGE EXAMPLE: jsonHandler -f /path/to/your/file/file.json[FILE][REQUIRED] -o member[OBJECT][OPTIONAL] \n" +
    "-s '<>'[SEPARATOR][OPTIONAL] -p y[PARALLEL][OPTIONAL] -t y [TESTLAUNCHER][OPTIONAL] -- [OPTIONS: -f, -o, -s, -p, -t]")

  protected def argsParser(map: ParserMap, l: List[String]): ParserMap = {
    def withPar(opt: String) = (opt(0) == '-')
    l match {
      case Nil => map
      case "-f" :: value :: t => argsParser(map ++ Map('InputFile -> value), t)
      case "-o" :: value :: t => argsParser(map ++ Map('ObjectParser -> value), t)
      case "-s" :: value :: t => argsParser(map ++ Map('Separator -> value), t)
      case "-p" :: value :: t => argsParser(map ++ Map('Parallel -> value), t)
      case "-t" :: value :: t => argsParser(map ++ Map('TestLauncher -> value), t)
      case s :: opt2 :: _ if withPar(opt2) => argsParser(map ++ Map('par -> s), l.tail)
      case _ => {
        println("\nINCORRECT COMMAND: " + l.reduce(_ + " " + _))
        usage
        map
      }
    }
  }

  protected def getArgument(p:ParserMap,s:Symbol):String=p.get(s).get

}

