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

    if(argsList.contains('Separator)){
      logger.info("{LAUNCHING JSON PARSER ON USING " + argsList.get('Separator).get + " AS SEPARATOR}")
      sep = argsList.get('Separator).get
    }

    argsList match {
    case x: JSONHandler.ParserMap if (x.contains('TestLauncher)) => //Launching Tests
      if(argValidator(x)("TestLauncher")("y")) {
        logger.debug("{LAUNCHING TESTS. PARAMETER PASSED: "+x.get('TestLauncher).get+"}")
        try {
          elementList.map(s => (inputTestValidator(ns, s), s)).foreach(y => launchTest(y._1,y._2))
        } catch {
          case ex: Exception => ex.printStackTrace; ex.getMessage
        }
      } else {
        logger.debug("{TESTS SKIPPED. PARAMETER PASSED: "+x.get('TestLauncher).get+"}")
      }
    case x: JSONHandler.ParserMap if (x.contains('ObjectParser)) => {
      logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
      parse(x('ObjectParser), x('InputFile))
    } // Launching for obj
    case x: JSONHandler.ParserMap if (x.contains('Parallel)) => {
      //the message will be replace with a new method
      logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
      //parse(x('Parallel),"")
    }
    case x: JSONHandler.ParserMap if (!x.contains('InputFile)) => {
      usage
      System.err.println("THE INPUT JSON FILE IS REQUIRED!")
      System.exit(1)
    }
    }
  }
}
