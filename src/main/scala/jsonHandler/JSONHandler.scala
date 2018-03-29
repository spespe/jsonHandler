package jsonHandler

import java.io.{FileInputStream, InputStreamReader}

import scala.io.Source
import scala.util.parsing.input.{Reader, StreamReader}

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object JSONHandler extends UtilT {
  def main(args: Array[String]) {
    //Datetime
    logger.info("{DATETIME: " + getTime + "}")

    if(args.size==0)usage
    val argsList = argsParser(Map(), args.toList)
    logger.info("{ARGUMENTS: " + argsList.toList.mkString(",") + "}")

    var sep = "|"
    var ObjParser:Parser[Any] = null
    val reader:Reader[Char] = StreamReader(new InputStreamReader(new FileInputStream(getArgument(argsList, 'InputFile))))
    
    //Elems in inputTestValidator
    val ns = (elem \\ "unit")

    if (argsList.contains('Separator)) {
      sep = getArgument(argsList, 'Separator)
    }
    logger.info("{LAUNCHING JSON PARSER USING " + sep + " AS SEPARATOR}")
    parseAll(value,"value null")

    testParamCheck(ns, argsList)

    if (!argsList.contains('InputFile)) {
      usage
      System.err.println("INPUT JSON FILE REQUIRED!")
      System.exit(1)
    }

    if (argsList.contains('ObjectParser)){
      logger.info("{LAUNCHING JSON PARSER ON " + getArgument(argsList, 'InputFile) + "USING " + getArgument(argsList, 'ObjectParser) + "}")
      getArgument(argsList, 'ObjectParser) match {
        case "arr" => ObjParser = arr
        case "obj" => ObjParser = obj
        case "value" => ObjParser = value
        case "member" => ObjParser = member
      }
      parseAll(ObjParser, reader) match {
          //match case stat
          case Success(matched, _) => println(matched)
          case Failure(failMsg, _) => System.err.println("FAILURE: " + failMsg)
          case Error(errMsg, _) => System.err.println("ERROR: " + errMsg)
        }
      log(getArgument(argsList, 'ObjectParser))(getArgument(argsList, 'ObjectParser))
    } else {
      logger.info("{LAUNCHING JSON PARSER ON " + getArgument(argsList, 'InputFile) + "USING NORMAL FILE PARSING }")
      //parseAll(getArgument(argsList, 'ObjectParser), getArgument(argsList, 'InputFile))
      //log(getArgument(argsList, 'ObjectParser))(getArgument(argsList, 'ObjectParser))
    }
  }
}

