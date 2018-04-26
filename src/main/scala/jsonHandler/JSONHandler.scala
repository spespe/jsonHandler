package jsonHandler

import java.io.{FileInputStream, InputStreamReader, File}
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
  private val jsonExtension = ".json"
  logger.info("{CREATING STREAMREADER FROM INPUT FILE: "+getArgument(argsList, 'InputFile)+" }")
  //Elems in inputTestValidator
  val ns = (elem \\ "unit")


  if (argsList.contains('Separator)) {
    sep = getArgument(argsList, 'Separator).get
  }
  logger.info("{LAUNCHING JSON PARSER USING " + sep + " AS SEPARATOR}")

  testParamCheck(ns, argsList)

  if (!argsList.contains('InputFile) && !argsList.contains('Directory)) {
    usage
    System.err.println("WRONG INPUT!")
    System.exit(1)
  }

  if (argsList.contains('InputFile) && argsList.contains('Directory)) {
    usage
    System.err.println("USER CANNOT SPECIFY BOTH INPUT FILE AND INPUT FOLDER IN THE COMMAND!")
    System.exit(1)
  }

  def launcher(inputFile:String) = {
    val reader:Reader[Char] = StreamReader(new InputStreamReader(new FileInputStream(inputFile)))
    if (argsList.contains('ObjectParser)) {
      logger.info("{LAUNCHING JSON PARSER ON " + inputFile + "USING " + getArgument(argsList, 'ObjectParser).get + "}")
      getArgument(argsList, 'ObjectParser).get match {
        case "arr" => ObjParser = arr
        case "obj" => ObjParser = obj
        case "value" => ObjParser = value
        case "member" => ObjParser = member
      }
      if (argsList.contains('OutputFile)) {
        logger.info("{WRITING TO FILE " + getArgument(argsList, 'OutputFile) + " THE PARSED RESULT}")
        parserLaunch(ObjParser, reader).get.foreach(x => writeFile(getArgument(argsList, 'OutputFile).get, x))
      } else {
        println(parserLaunch(ObjParser, reader))
      }

    } else {
      logger.info("{LAUNCHING JSON PARSER ON " + inputFile + " USING NORMAL FILE PARSING }")
      if (argsList.contains('OutputFile)) {
        logger.info("{WRITING TO FILE " + getArgument(argsList, 'OutputFile) + " THE PARSED RESULT}")
        parserLaunch(multiple, reader).get.foreach(x => writeFile(getArgument(argsList, 'OutputFile).get, x))
      } else {
        println(parserLaunch(multiple, reader))
      }
    }
  }

  if (argsList.contains('Directory)) {
    val list = new java.io.File(getArgument(argsList, 'Directory).get.mkString).listFiles.filter(x=>x.toString.endsWith(jsonExtension))
    list.foreach(x=>launcher(x.toString))
  } else {
    launcher(getArgument(argsList, 'InputFile).get)
  }

}



