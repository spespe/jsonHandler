package testClasses

import jsonHandler.{JSONParser, UtilT}
import org.scalatest.FunSuite

import scala.util.Try
import scala.xml.NodeSeq

/**
  * Created by Pietro.Speri on 07/02/2018.
  */

class ArgParserTest(nd: NodeSeq) extends FunSuite with JSONParser with UtilT {

  for {(elem, index) <- (nd \\ "@name").zipWithIndex} {
    test(elem.text) {
      val (result, expected) = testResArgs((nd \\ "test") (index).text.split(" "))((nd \\ "result") (index).text)
      try {assert(result == expected)}
      catch {
        case _: Exception => {
          System.err.println(result + " IT IS NOT EQUAL TO " + expected)
          System.err.println("Result = Failed")
        }
      }
    }
  }

  private def testResArgs(test: Array[String])(expectedResult: String): (String, String) = {
      val aargs = argsParser(Map(), test.toList)
      (aargs.toString.stripMargin.trim, expectedResult.stripMargin.trim)
  }

}

