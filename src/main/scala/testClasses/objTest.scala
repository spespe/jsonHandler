package testClasses

import jsonHandler.JSONParser
import org.scalatest.FunSuite

import scala.xml.{NodeSeq}

/**
  * Created by Pietro.Speri on 07/02/2018.
  */
class objTest (nd:NodeSeq) extends FunSuite with JSONParser {

  def testRes(test:String,expectedResult:String):(String,String)={
    val result = parse(obj,test) match {
      case Success(matched, _) => matched
      case Failure(failMsg, _) => System.err.println("FAILURE: "+failMsg)
      case Error(errMsg, _) => System.err.println("ERROR: "+errMsg)
    }
    (result.toString.trim,expectedResult.stripMargin.trim)
  }

  var res=""
  for {
    (elem,index)<-(nd \\ "@name").zipWithIndex
  }{
    test(elem.text) {
      val(result,expected)=testRes((nd \\ "test")(index).text,(nd \\ "result")(index).text)
      try {
        assert(result == expected)
        res="Passed"
      }
      catch {
        case e: Exception => println("Result = Failed")
          res = "Failed"
      }
    }
  }
}
