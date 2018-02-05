import java.util.Calendar

import com.typesafe.scalalogging.LazyLogging

import scala.util.parsing.json.JSON

/**
  * Created by Pietro.Speri on 26/01/2018.
  */

object jsonHandler extends JSON with LazyLogging with ArgumentsParser {

  def main(args:Array[String]){
    logger.info("{DATETIME}")
    println(Calendar.getInstance.getTime)

    logger.info("{CHECKING INPUT ARGUMENTS}")
    val argsList = args.toList

    logger.info("{PARAMETERS}")
    argsParser(Map(),argsList).foreach(println)

    }
}

