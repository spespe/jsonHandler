import org.scalatest.FunSuite

import scala.xml.Node

/**
  * Created by Pietro.Speri on 07/02/2018.
  */
class arrTest (seq:Seq[(((Node,Node),Int),Node)]) extends FunSuite {
  for(((el,idx),name)<-seq){
    test("ARRAY TEST NUMBER: "+idx+" NAME:"+name){
      val res = el._1
      val expRes = el._2
      assert(res==expRes)
    }
  }
}