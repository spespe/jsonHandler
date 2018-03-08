import java.io.{FileInputStream, InputStreamReader}
import java.util.Calendar
import com.typesafe.scalalogging.LazyLogging
import scala.util.parsing.input.StreamReader
import org.scalatest.FunSuite
import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object jsonHandler extends FunSuite with utilTest with ArgumentsParser with LazyLogging {

  class JSON extends JavaTokenParsers {
    def value:Parser[Any] = (obj | arr | stringLiteral | floatingPointNumber ^^ (_.toDouble) |
      "null" ^^ {x => null} |
      "true" ^^ {x => true} |
      "false" ^^ {x => false})
    def obj:Parser[Map[String,Any]] = "{"~>repsep(member,",")<~"}" ^^ (Map() ++ _)
    def arr:Parser[List[Any]] = "["~>repsep(value, ",")<~"]"
    def member:Parser[(String,Any)] = stringLiteral~":"~value ^^ {case name~":"~value => (name,value)}
  }

  def main(args:Array[String]){
      logger.info("{DATETIME}")
      println(Calendar.getInstance.getTime)

      logger.info("{CHECKING INPUT ARGUMENTS}")
      val argsList = argsParser(Map(),args.toList)
      if(!argsList.contains('InputFile)){usage;System.err.println("THE INPUT JSON FILE IS REQUIRED!");System.exit(1)}
      argsList('InputFile)
      argsList.foreach(println)

      //File reader
      val reader = StreamReader(new InputStreamReader(new FileInputStream(argsList('InputFile))))

      argsList.foreach(_ => {
          case x:jsonHandler.ParserMap if(x.contains('TestLauncher)) => //Launching Tests
              try {
                  val argsList = argsParser(Map(),args.toList)
                  val value = inputTestValidator("value").get
                  val obj = inputTestValidator("obj").get
                  val member = inputTestValidator("member").get
                  val arr = inputTestValidator("arr").get

                  logger.info("{DATETIME}")
                  getTime

                  List("value", "obj", "member", "arr").map(x => (inputTestValidator(x).get, x)).
                    foreach( x => {
                        logger.debug("{LAUNCHING TESTS FOR " + x._2 + "}")
                        run(new valueTest(seqCreator(x._1)))
                    }
                    )
              } catch {
                  case ex: Exception => ex.printStackTrace; ex.getMessage
              }
          case x:jsonHandler.ParserMap if(x.contains('ObjectParser)) => {
              //parse(x('ObjectParser),"")
          } // Launching for obj
          case _ => System.err.println("THE PARAMETER PASSED IS INVALID");usage;System.exit(1)
          }
      )

      //"C:\\Users\\pietro.speri\\IdeaProjects\\jsonHandler\\src\\test\\resources\\jsonExample.json"
      //Case to be added to separate the options

      //parse(value,"0.9") match {
      //    case Success(succMatch, _) => println("SUCCESS: "+succMatch)
      //    case Failure(failMsg, _) => println("FAILED: "+failMsg)
      //    case Error(errMsg, _) => println("ERROR: "+errMsg)
      //}
      //parse(obj,"") match {
      //    case Success(succMatch, _) => println("SUCCESS: "+succMatch)
      //    case Failure(failMsg, _) => println("FAILED: "+failMsg)
      //    case Error(errMsg, _) => println("ERROR: "+errMsg)
      //}
      //parse(arr,"") match {
      //    case Success(succMatch, _) => println("SUCCESS: "+succMatch)
      //    case Failure(failMsg, _) => println("FAILED: "+failMsg)
      //    case Error(errMsg, _) => println("ERROR: "+errMsg)
      //}
      //parse(member,"") match {
      //    case Success(succMatch, _) => println("SUCCESS: "+succMatch)
      //    case Failure(failMsg, _) => println("FAILED: "+failMsg)
      //    case Error(errMsg, _) => println("ERROR: "+errMsg)
      //}
    }
}

