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
    val elementList = List("value", "obj", "member", "arr")
    val ns = (elem \\ "unit")

    if(argsList.contains('Separator)){sep = getArgument(argsList,'Separator)}
    logger.info("{LAUNCHING JSON PARSER ON USING " + sep + " AS SEPARATOR}")

    argsList match {
      case x: JSONHandler.ParserMap if !x.contains('InputFile) => {
        usage
        System.err.println("THE INPUT JSON FILE IS REQUIRED!")
        System.exit(1)
      }
      case x: JSONHandler.ParserMap if x.contains('TestLauncher) =>
        if(argValidator(x)('TestLauncher)("y")) {
          logger.debug("{LAUNCHING TESTS. TESTS PARAMETER PASSED: "+ getArgument(x,'TestLauncher) +"}")
        try {
          elementList.map(s => (inputTestValidator(ns, s), s)).foreach(y => launchTest(y._1,y._2))
        } catch {
          case ex: Exception => ex.printStackTrace; ex.getMessage
        }
      } else {
          logger.debug("{TESTS SKIPPED. TESTS PARAMETER PASSED: "+getArgument(x,'TestLauncher)+"}")
      }
      case x: JSONHandler.ParserMap if x.contains('ObjectParser) => {
          logger.info("{LAUNCHING JSON PARSER ON " + getArgument(x,'InputFile) + "USING " + getArgument(x,'ObjectParser) + "}")
        parse(getArgument(x,'ObjectParser), getArgument(x,'InputFile))
      }
    }
  }
}

