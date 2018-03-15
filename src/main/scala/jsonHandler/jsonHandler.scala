package jsonHandler

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.run
import testClasses.{arrTest, memberTest, objTest, valueTest}

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object jsonHandler extends App with utilT with ArgumentsParser with LazyLogging with JSONParser with utilT{

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
      case x: jsonHandler.ParserMap if (x.contains('TestLauncher)) => //Launching Tests
        try {
          elementList.map(s => (inputTestValidator(ns, s),s)).
            foreach(y =>
              y._2 match {
                case "value" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new valueTest(y._1))
                case "obj" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new objTest(y._1))
                case "member" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new memberTest(y._1))
                case "arr" => logger.info("{LAUNCHING TESTS FOR " + y._2 + "}"); run(new arrTest(y._1))
              }
            )
        } catch {
          case ex: Exception => ex.printStackTrace; ex.getMessage
        }
      case x: jsonHandler.ParserMap if (x.contains('ObjectParser)) => {
        logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
        parse(x('ObjectParser),x('InputFile))
      } // Launching for obj
      case x: jsonHandler.ParserMap if (x.contains('Separator)) => {
        logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
        parse(x('Separator),x('InputFile))
      }
      case x: jsonHandler.ParserMap if (x.contains('Parallel)) => {
        //the message will be replace with a new method
        logger.info("{LAUNCHING JSON PARSER ON " + x('InputFile) + "USING " + x('ObjectParser) + "}")
        //parse(x('Parallel),"")
      }
      case x: jsonHandler.ParserMap if (!x.contains('InputFile)) => {
        usage
        System.err.println("THE INPUT JSON FILE IS REQUIRED!")
        System.exit(1)
      }
    }
}
