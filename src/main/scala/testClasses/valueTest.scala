package testClasses

import jsonHandler.JSONParser
import org.scalatest.FunSuite
import scala.xml.Node

/**
  * Created by Pietro.Speri on 05/02/2018.
  */

class valueTest(seq:IndexedSeq[(((Char,Char),Int),Char)]) extends FunSuite with JSONParser {
  for(((el,idx),name)<-seq){
    println(el._1)
    test("VALUE TEST NUMBER: "+idx+" NAME:"+name){
      val res = parse(value, el._1) match {
        case Success(matched, _) => println(matched)
        case Failure(failMsg, _) => println("FAILURE: "+failMsg)
        case Error(errMsg, _) => println("ERROR: "+errMsg)
      }
      val expRes = el._2
      assert(res==expRes)
    }
  }
}
