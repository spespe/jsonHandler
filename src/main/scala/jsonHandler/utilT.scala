package jsonHandler

import java.util.Calendar

import scala.xml.{Node, NodeSeq, XML}

/**
  * Created by Pietro.Speri on 07/03/2018.
  */

trait utilT {

  def getTime=println(Calendar.getInstance.getTime)
  @volatile var elem: scala.xml.Elem = _
  val input = getClass.getResourceAsStream("/tests.xml")
  try {
      elem = XML.load(input)
        } catch {
          case ex:Throwable => System.err.println("[NOT ABLE TO LOAD THE XML FILE]")
            ex.printStackTrace
            System.exit(1)
        }

          //Test sequence creator method
          def seqCreator(nd:NodeSeq):IndexedSeq[(((Char,Char),Int),Char)]={
            val test:String = (nd \\ "unit" \\ "test").text
            val result:String = (nd \\ "unit" \\ "result").text
            val name:String = (nd \\ "@name").text
            test.zip(result).zipWithIndex.zip(name)
          }

          def inputTestValidator(s:String):Option[NodeSeq]={
            (elem \\ "unit").filter(i => i.attribute("tag").get.text == s) match {
              case n:NodeSeq => Some(n)
              case _ => println("INPUT NOT FOUND IN THE TESTS.\n"); None
            }
          }
        }
