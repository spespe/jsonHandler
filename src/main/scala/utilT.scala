import java.util.Calendar
import scala.xml.{Node, NodeSeq, XML}

/**
  * Created by Pietro.Speri on 07/03/2018.
  */

trait utilT {

  def getTime=println(Calendar.getInstance.getTime)

  var elem: scala.xml.Elem = _
  val input = Some(getClass.getResourceAsStream("/tests.xml"))
  try {
    elem = XML.load(input.get)
  } catch {
    case ex:Throwable => System.err.println("[NOT ABLE TO LOAD THE XML FILE]")
      ex.printStackTrace()
      System.exit(1)
  }

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
  }

