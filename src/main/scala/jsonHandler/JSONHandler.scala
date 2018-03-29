package jsonHandler

import java.io.{FileInputStream, InputStreamReader}
import scala.util.parsing.input.{Reader, StreamReader}

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object JSONHandler extends UtilT with App {
    //Datetime
    logger.info("{DATETIME: " + getTime + "}")

    if(args.size==0)usage
    private val argsList = argsParser(Map(), args.toList)
    logger.info("{ARGUMENTS: " + argsList.toList.mkString(",") + "}")

    private var sep = "|"
    private var ObjParser:Parser[Any] = null
    logger.info("{CREATING STREAMREADER FROM INPUT FILE: "+getArgument(argsList, 'InputFile)+" }")
    private val reader:Reader[Char] = StreamReader(new InputStreamReader(new FileInputStream(getArgument(argsList, 'InputFile))))

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
          case Success(matched, _) => println(matched) //Adding writer
          case Failure(failMsg, _) => System.err.println("FAILURE: " + failMsg)
          case Error(errMsg, _) => System.err.println("ERROR: " + errMsg)
        }
    } else {
      logger.info("{LAUNCHING JSON PARSER ON " + getArgument(argsList, 'InputFile) + "USING NORMAL FILE PARSING }")
      parseAll(value, reader) match {
        case Success(matched, _) => println(matched) //Adding writer
        case Failure(failMsg, _) => System.err.println("FAILURE: " + failMsg)
        case Error(errMsg, _) => System.err.println("ERROR: " + errMsg)
      }
    }
}

