package testClasses

import jsonHandler.JSONParser
import org.scalatest.FunSuite
import scala.xml.NodeSeq

/**
  * Created by Pietro.Speri on 07/02/2018.
  */
class ArrTest(nd: NodeSeq) extends FunSuite with JSONParser {

  def testRes(test: String, expectedResult: String): (String, String) = {
    val result = parse(arr, test) match {
      case Success(matched, _) => matched
      case Failure(failMsg, _) => System.err.println("TEST: "+test+" FAILURE: " + failMsg)
      case Error(errMsg, _) => System.err.println("ERROR: " + errMsg)
    }
    (result.toString.trim, expectedResult.stripMargin.trim)
  }
  for {
    (elem, index) <- (nd \\ "@name").zipWithIndex
  } {
    test(elem.text) {
      val (result, expected) = testRes((nd \\ "test") (index).text, (nd \\ "result") (index).text)
      try {
        assert(result == expected)
      }
      catch {
        case _: Exception => {
          System.err.println(result + "IT IS NOT EQUAL TO " + expected)
          System.err.println("Result = Failed")
        }
      }
    }
  }

}
