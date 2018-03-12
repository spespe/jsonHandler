package jsonHandler

import java.util.Calendar
import scala.io.Source
import scala.xml.{Node, NodeSeq, XML}

/**
  * Created by Pietro.Speri on 07/03/2018.
  * */

trait utilT {

  def getTime=println(Calendar.getInstance.getTime)
  @volatile var elem: scala.xml.Elem = _
//  val input = getClass.getResourceAsStream("/tests.xml")
//  val input= "C:\\Users\\pietro.speri\\IdeaProjects\\jsonHandler\\src\\main\\scala\\resources\\tests.xml"
  try {
      elem = XML.load("C:\\Users\\pietro.speri\\IdeaProjects\\jsonHandler\\src\\main\\scala\\resources\\tests.xml")
        } catch {
          case ex:Throwable => System.err.println("[NOT ABLE TO LOAD THE XML FILE]")
            ex.printStackTrace
            System.exit(1)
        }

          //Test sequence creator method
          def seqCreator(nd:NodeSeq):Seq[(((NodeSeq,NodeSeq),Int),NodeSeq)]={
            val test:NodeSeq = (nd \\ "unit" \\ "test")
            val result:NodeSeq = (nd \\ "unit" \\ "result")
            val name:NodeSeq = (nd \\ "@name")
//            xml.XML.load(test).zip(xml.XML.load(result)).zipWithIndex.zip(xml.XML.load(name))
            test.zip(result).zipWithIndex.zip(name)
          }

          def inputTestValidator(s:String):NodeSeq={
            (elem \\ "@name").zipWithIndex
            (elem \\ "unit").filter(i => i.attribute("tag").get.text == s) match {
              case n:NodeSeq => n
              case _ => throw new MatchError("INPUT NOT FOUND IN THE TESTS.\n")
            }
          }
}
