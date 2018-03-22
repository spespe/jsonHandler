package jsonHandler

import com.typesafe.scalalogging.LazyLogging
import testClasses.{ArrTest, MemberTest, ObjTest, ValueTest}

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object JSONHandler extends UtilT with LazyLogging {

  def main(args:Array[String]){
  //Datetime
  logger.info("{DATETIME: "+getTime+"}")

  val argsList = argsParser(Map(), args.toList)
  logger.info("{ARGUMENTS: "+argsList.toList.mkString(",")+"}")

  //Elems in inputTestValidator
  val elementList = List("value", "obj", "member", "arr")
  val ns = (elem \\ "unit")

  argsList match {
    case x: JSONHandler.ParserMap if (x.contains('TestLauncher)) => //Launching Tests
      if(argValidator(x)("TestLauncher")("y")) {
        logger.debug("{LAUNCHING TESTS. PARAMETER PASSED: "+x.get('TestLauncher).get+"}")
        try {
          elementList.map(s => (inputTestValidator(ns, s), s)).
            foreach(y =>
              y._2 match {
                case "value" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); org.scalatest.run(new ValueTest(y._1))
                case "obj" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); org.scalatest.run(new ObjTest(y._1))
                case "member" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); org.scalatest.run(new MemberTest(y._1))
                case "arr" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); org.scalatest.run(new ArrTest(y._1))
              }
            )
        } catch {
          case ex: Exception => ex.printStackTrace; ex.getMessage
        }
      } else {
        logger.debug("{TESTS ARE NOT GOING TO BE LAUNCHED. PARAMETER PASSED: "+x.get('TestLauncher).get+"}")
      }
    case x: JSONHandler.ParserMap if (x.contains('ObjectParser)) => {
      logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
      parse(x('ObjectParser), x('InputFile))
    } // Launching for obj
    case x: JSONHandler.ParserMap if (x.contains('Separator)) => {
      logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
      parse(x('Separator), x('InputFile))
    }
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