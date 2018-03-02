import java.io.{FileInputStream, InputStreamReader}
import scala.io.Source
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
      val argsList = args.toList

      logger.info("{PARAMETERS}")
      argsParser(Map(),argsList).foreach(println)

      //File reader
      val reader = StreamReader(new InputStreamReader(new FileInputStream("C:\\Users\\pietro.speri\\IdeaProjects\\jsonHandler\\src\\test\\resources\\jsonExample.json")))
      //Case to be added to separate the options
      parse(value,reader) match {
          case Success(succMatch, _) => succMatch
          case Failure(failMsg, _) => println("FAILED: "+failMsg)
          case Error(errMsg, _) => println("ERROR: "+errMsg)
      }
    }
}

