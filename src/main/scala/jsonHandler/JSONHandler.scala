package jsonHandler

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object JSONHandler extends UtilT {
  def main(args:Array[String]){
    //Datetime
    logger.info("{DATETIME: "+getTime+"}")

    var sep = "|"
    val argsList = argsParser(Map(), args.toList)
    logger.info("{ARGUMENTS: "+argsList.toList.mkString(",")+"}")

    //Elems in inputTestValidator

    val ns = (elem \\ "unit")

    if(argsList.contains('Separator)){sep = getArgument(argsList,'Separator)}
    logger.info("{LAUNCHING JSON PARSER USING " + sep + " AS SEPARATOR}")

    testParamCheck(ns,argsList)

    argsList match {
      case x: JSONHandler.ParserMap if !x.contains('InputFile) => {
        usage
        System.err.println("INPUT JSON FILE REQUIRED!")
        System.exit(1)
      }
      case x: JSONHandler.ParserMap if x.contains('ObjectParser) => {
          logger.info("{LAUNCHING JSON PARSER ON " + getArgument(x,'InputFile) + "USING " + getArgument(x,'ObjectParser) + "}")
        parse(getArgument(x,'ObjectParser), getArgument(x,'InputFile))
        log(getArgument(x,'ObjectParser))(getArgument(x,'ObjectParser))
      }
    }
  }
}

