/**
  * Created by Pietro.Speri on 05/02/2018.
  */
import org.scalatest.FunSuite
import scala.language._
import scala.xml.{Node}

class valueTest(seq:Seq[(((Node,Node),Int),Node)]) extends FunSuite {
  for(((el,idx),name)<-seq){
    test("VALUE TEST NUMBER: "+idx+" NAME:"+name){
      val res = el._1
      val expRes = el._2
      assert(res==expRes)
    }
  }
}
