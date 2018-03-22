package jsonHandler

import java.util.Calendar
import org.scalatest.FunSuite
import scala.xml.{NodeSeq, XML}

/**
  * Created by Pietro.Speri on 07/03/2018.
  **/

trait UtilT extends FunSuite with ArgumentsParser with JSONParser {

  @volatile var elem: scala.xml.Elem = _

  def getTime = Calendar.getInstance.getTime
  //  val input = getClass.getResourceAsStream("/tests.xml")
  //  val input= "C:\\Users\\pietro.speri\\IdeaProjects\\jsonHandler\\src\\main\\scala\\resources\\tests.xml"

  try {
    elem = XML.load("C:\\Users\\pietro.speri\\IdeaProjects\\jsonHandler\\src\\main\\scala\\resources\\tests.xml")
  } catch {
    case ex: Throwable => System.err.println("[NOT ABLE TO LOAD THE XML FILE]")
      ex.printStackTrace
      System.exit(1)
  }

  //Test sequence creator method
  def seqCreator(nd: NodeSeq): Seq[(((NodeSeq, NodeSeq), Int), NodeSeq)] = {
    val test: NodeSeq = (nd \\ "unit" \\ "test")
    val result: NodeSeq = (nd \\ "unit" \\ "result")
    val name: NodeSeq = (nd \\ "@name")
    test.zip(result).zipWithIndex.zip(name)
  }

  def inputTestValidator(ns: NodeSeq, s: String): NodeSeq = {
    ns.filter(i => i.attribute("tag").get.text == s) match {
      case n: NodeSeq => n
      case _ => throw new MatchError("INPUT NOT FOUND IN THE TESTS.\n")
    }
  }

  def argValidator(p:ParserMap)(s:String)(s2:String):Boolean=if(p.get(Symbol(s))==Some(s2)) true else false

  def testRes(test: String, expectedResult: String): (String, String) = {
    val result = parse(obj, test) match {
      case Success(matched, _) => matched
      case Failure(failMsg, _) => System.err.println("TEST: "+test+" FAILURE: " + failMsg)
      case Error(errMsg, _) => System.err.println("ERROR: " + errMsg)
    }
    (result.toString.stripMargin.trim, expectedResult.stripMargin.trim)
  }

  def resultValidator(nd:NodeSeq)= {
    for {(elem, index) <- (nd \\ "@name").zipWithIndex} {
      test(elem.text) {
        val (result, expected) = testRes((nd \\ "test") (index).text, (nd \\ "result") (index).text)
        try {assert(result == expected)}
        catch {
          case _: Exception => {
            System.err.println(result + "IT IS NOT EQUAL TO " + expected)
            System.err.println("Result = Failed")
          }
        }
      }
    }
  }

}
