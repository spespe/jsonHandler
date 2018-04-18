package jsonHandler

import java.io.{File, FileInputStream, InputStreamReader}

import scala.util.parsing.input.{Reader, StreamReader}

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object JSONHandler extends Util with App {
  //Datetime
  logger.info("{DATETIME: " + getTime + "}")

  if(args.size==0)usage
  val argsList = argsParser(Map(), args.toList)
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
    writeFile(new File(""),parserLaunch(ObjParser, reader).asInstanceOf[Array[Char]])
  } else {
    logger.info("{LAUNCHING JSON PARSER ON " + getArsgument(argsList, 'InputFile) + " USING NORMAL FILE PARSING }")
    parserLaunch(multiple, reader)
  }

}



