package jsonHandler

import scala.util.parsing.input.Reader

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object JSONHandler extends UtilT {
  def main(args: Array[String]) {
    //Datetime
    logger.info("{DATETIME: " + getTime + "}")

    var sep = "|"

    if(args.size==0)usage
    val argsList = argsParser(Map(), args.toList)
    logger.info("{ARGUMENTS: " + argsList.toList.mkString(",") + "}")

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
      parseAll(getArgument(argsList, 'ObjectParser).asInstanceOf[Parser[Any]]/**casting as Parser[Any] for now**/, getArgument(argsList, 'InputFile)) match {
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

