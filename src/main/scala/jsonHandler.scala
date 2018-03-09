import java.io.{FileInputStream, InputStreamReader}
import com.typesafe.scalalogging.LazyLogging
import scala.util.parsing.input.StreamReader
import org.scalatest.run
import testClasses.{arrTest, memberTest, objTest, valueTest}
import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object jsonHandler extends App with utilT with ArgumentsParser with LazyLogging {

  class JSON extends JavaTokenParsers {
    def value: Parser[Any] = (obj | arr | stringLiteral | floatingPointNumber ^^ (_.toDouble) |
      "null" ^^ { x => null } |
      "true" ^^ { x => true } |
      "false" ^^ { x => false })

    def obj: Parser[Map[String, Any]] = "{" ~> repsep(member, ",") <~ "}" ^^ (Map() ++ _)

    def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

    def member: Parser[(String, Any)] = stringLiteral ~ ":" ~ value ^^ { case name ~ ":" ~ value => (name, value) }
  }

  logger.info("{DATETIME}")
  getTime

//  List("value", "obj", "member", "arr").map(x => (inputTestValidator(x).get, x)).foreach(println)


  logger.info("{CHECKING INPUT ARGUMENTS}")
    val argsList = argsParser(Map(), args.toList)
    if (!argsList.contains('InputFile)) {
      usage;
      System.err.println("THE INPUT JSON FILE IS REQUIRED!");
      System.exit(1)
    }

    //Elems in inputTestValidator
    val elementList = List("value", "obj", "member", "arr")
    val value = inputTestValidator("value").get
    val obj = inputTestValidator("obj").get
    val member = inputTestValidator("member").get
    val arr = inputTestValidator("arr").get

    logger.info("{DATETIME}")
    getTime

    //File reader
    //val reader = StreamReader(new InputStreamReader(new FileInputStream(argsList('InputFile))))

    argsList.foreach(x =>
      x match {
        case x: jsonHandler.ParserMap if (x.contains('TestLauncher)) => //Launching Tests
          try {
            elementList.map(x => (inputTestValidator(x).get, x)).
              foreach(x =>
                x._2 match {
                  //In the future a shell script will generate the jar and will launch the tests on it
                  case "value" => logger.debug("{LAUNCHING TESTS FOR " + x._2 + "}"); run(new valueTest(seqCreator(x._1)))
                  case "obj" => logger.debug("{LAUNCHING TESTS FOR " + x._2 + "}"); run(new objTest(seqCreator(x._1)))
                  case "member" => logger.debug("{LAUNCHING TESTS FOR " + x._2 + "}"); run(new memberTest(seqCreator(x._1)))
                  case "arr" => logger.debug("{LAUNCHING TESTS FOR " + x._2 + "}"); run(new arrTest(seqCreator(x._1)))
                }
              )
          } catch {
            case ex: Exception => ex.printStackTrace; ex.getMessage
          }
        case x: jsonHandler.ParserMap if (x.contains('ObjectParser)) => {
          //parse(x('ObjectParser),"")
        } // Launching for obj
        case x: jsonHandler.ParserMap if (x.contains('Separator)) => {
          //parse(x('Separator),"")
        }
        case x: jsonHandler.ParserMap if (x.contains('Parallel)) => {
          //parse(x('Parallel),"")
        }
        case x: jsonHandler.ParserMap if (x.contains('TestLauncher)) => {
          //parse(x('TestLauncher),"")
        }
        case _ => System.err.println("THE PARAMETER PASSED IS INVALID"); usage; System.exit(1)
      }
    )
}
