package testClasses

/**
  * Created by Pietro.Speri on 05/02/2018.
  */
import org.scalatest.FunSuite

import scala.xml.Node

class valueTest(seq:Seq[(((Node,Node),Int),Node)]) extends FunSuite{
  for(((el,idx),name)<-seq){
    //test("VALUE TEST NUMBER: "+idx+" NAME:"+name){
      //val res = parse(value, el._1) match {
      //  case Success(matched _) => matched
      //  case Failure(failMsg, _) => println("FAILURE: "+msg)
      //  case Error(errMsg, _) => println("ERROR: "+errMsg)
      //}
      //val expRes = el._2
      //assert(res==expRes)
  //  }
  }
}
