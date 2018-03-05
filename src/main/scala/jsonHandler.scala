import java.io.{FileInputStream, InputStreamReader}
import java.util.Calendar
import com.typesafe.scalalogging.LazyLogging
import scala.util.parsing.input.StreamReader

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object jsonHandler extends JSON with ArgumentsParser with LazyLogging {
  def main(args:Array[String]){
      logger.info("{DATETIME}")
      println(Calendar.getInstance.getTime)

      logger.info("{CHECKING INPUT ARGUMENTS}")
      val argsList = argsParser(Map(),args.toList)
      if(!argsList.contains('InputFile)){usage;System.err.println("THE INPUT JSON FILE IS REQUIRED!")}
      argsList.foreach(println)

      //File reader
      //val reader = StreamReader(new InputStreamReader(new FileInputStream("")))
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

