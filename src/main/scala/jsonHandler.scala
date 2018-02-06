import java.io.FileReader
import java.util.Calendar
import com.typesafe.scalalogging.LazyLogging

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

    }
}

