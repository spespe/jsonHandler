package testClasses

import jsonHandler.JSONParser
import org.scalatest.FunSuite

import scala.xml.Node

/**
  * Created by Pietro.Speri on 07/02/2018.
  */
class arrTest (seq:Seq[(((Node,Node),Int),Node)]) extends FunSuite with JSONParser {
  for(((el,idx),name)<-seq){
    test("ARRAY TEST NUMBER: "+idx+" NAME:"+name){
      val res = parse(arr, el._1.asInstanceOf[CharSequence]) match {
        case Success(matched, _) => matched
        case Failure(failMsg, _) => println("FAILURE: "+failMsg)
        case Error(errMsg, _) => println("ERROR: "+errMsg)
      }
      val expRes = el._2
      assert(res==expRes)
    }
  }
}
