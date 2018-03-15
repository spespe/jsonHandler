package jsonHandler

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.run
import testClasses.{ArrTest, MemberTest, ObjTest, ValueTest}

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object JsonHandler extends App with ArgumentsParser with LazyLogging with JSONParser with UtilT{

    logger.info("{DATETIME}")
    getTime

    logger.info("{CHECKING INPUT ARGUMENTS} : ")
    val argsList = argsParser(Map(), args.toList)
    logger.info(argsList.toList.mkString(","))

    //Elems in inputTestValidator
    val elementList = List("value", "obj", "member", "arr")
    val ns = (elem \\ "unit")

    //Datetime
    logger.info("{DATETIME}")
    getTime

  //  val reader = StreamReader(new InputStreamReader(new FileInputStream(argsList('InputFile))))
    argsList match {
      case x: JsonHandler.ParserMap if (x.contains('TestLauncher)) => //Launching Tests
        try {
          elementList.map(s => (inputTestValidator(ns, s),s)).
            foreach(y =>
              y._2 match {
                case "value" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new ValueTest(y._1))
                case "obj" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new ObjTest(y._1))
                case "member" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new MemberTest(y._1))
                case "arr" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new ArrTest(y._1))
              }
            )
        } catch {
          case ex: Exception => ex.printStackTrace; ex.getMessage
        }
      case x: JsonHandler.ParserMap if (x.contains('ObjectParser)) => {
        logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
        parse(x('ObjectParser),x('InputFile))
      } // Launching for obj
      case x: JsonHandler.ParserMap if (x.contains('Separator)) => {
        logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
        parse(x('Separator),x('InputFile))
      }
      case x: JsonHandler.ParserMap if (x.contains('Parallel)) => {
        //the message will be replace with a new method
        logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
        //parse(x('Parallel),"")
      }
      case x: JsonHandler.ParserMap if (!x.contains('InputFile)) => {
        usage
        System.err.println("THE INPUT JSON FILE IS REQUIRED!")
        System.exit(1)
      }
    }
}
