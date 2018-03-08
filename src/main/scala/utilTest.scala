import java.util.Calendar

import jsonHandler.getClass

import scala.xml.{Node, NodeSeq, XML}

/**
  * Created by Pietro.Speri on 07/03/2018.
  */
trait utilTest {

  var elem: scala.xml.Elem = _
  val input = Some(getClass.getResourceAsStream("/tests.xml"))
  elem = XML.load(input.get)

  //Test sequence creator method
  def seqCreator(nd:NodeSeq):Seq[(((Node,Node),Int),Node)]={
    val test = (nd \\ "test")
    val result = (nd \\ "result")
    val name = (nd \\ "@name")
    test.zip(result).zipWithIndex.zip(name)
  }
  def inputTestValidator(s:String):Option[NodeSeq]={
    (elem \\ "unit").filter(i => i.attribute("tag").get.text == s) match {
      case n:NodeSeq => Some(n)
      case _ => println("INPUT NOT FOUND IN THE TESTS.\n"); None
    }
  }

  def getTime=println(Calendar.getInstance.getTime)


}
