package jsonHandler

import scala.annotation.tailrec

/**
  * Created by Pietro.Speri on 31/01/2018.
  */

trait ArgumentsParser {

  protected type ParserMap = Map[Symbol, String]

  protected def usage = println("\nOne or more arguments passed are incorrect. Please refer to the following usage example.\n" +
    "USAGE EXAMPLE: jsonHandler -f /path/to/your/file/file.json[FILE][REQUIRED] -k key [KEY][OPTIONAL] \n" +
    "-s '<*>'[SEPARATOR][OPTIONAL] -t y [TESTLAUNCHER][OPTIONAL] -o /path/to/output/file.txt")

  @tailrec
  protected final def argsParser(map: ParserMap, l: List[String]): ParserMap = {
    l match {
      case Nil => map
      case "-f" :: value :: t => argsParser(map ++ Map('InputFile -> value), t)
      case "-k" :: value :: t => argsParser(map ++ Map('Key -> value), t)
      //case "-s" :: value :: t => argsParser(map ++ Map('Separator -> value), t)
      case "-t" :: value :: t => argsParser(map ++ Map('TestLauncher -> value), t)
      case "-d" :: value :: t => argsParser(map ++ Map('Directory -> value), t)
      case "-o" :: value :: t => argsParser(map ++ Map('OutputFile -> value), t)
      case s:List[String] => {
        usage
        throw new IllegalArgumentException("The argument passed (" +s(0)+ ") has not been recognized")
      }
    }
  }

  protected def getArgument(p:ParserMap,s:Symbol):Option[String]=p.get(s)

}

