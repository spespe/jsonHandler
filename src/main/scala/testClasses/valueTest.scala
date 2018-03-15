package testClasses

import jsonHandler.{JSONParser, utilT}
import org.scalatest.FunSuite

/**
  * Created by Pietro.Speri on 05/02/2018.
  */

class valueTest extends FunSuite with JSONParser with utilT{
  var nd = (elem \\ "unit").filter(i=>i.attribute("tag").get.text=="value")
  def testRes(test:String,expectedResult:String):(String,String)={
    val result = parse(value,test) match {
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



  //for(((el,idx),name)<-seq){
  //  println(el._1)
  //  test("VALUE TEST NUMBER: "+idx+" NAME:"+name){
  //    val res = parse(value, el._1.mkString) match {
  //      case Success(matched, _) => println(matched)
  //      case Failure(failMsg, _) => println("FAILURE: "+failMsg)
  //      case Error(errMsg, _) => println("ERROR: "+errMsg)
  //    }
  //    val expRes = el._2
  //    assert(res==expRes)
  //  }
  //}
}

