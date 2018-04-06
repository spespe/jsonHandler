package jsonHandler

import java.util.Calendar
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.FunSuite
import testClasses._
import scala.xml.{NodeSeq, XML}

/**
  * Created by Pietro.Speri on 07/03/2018.
  **/

trait Util extends FunSuite with ArgumentsParser with JSONParser with LazyLogging {

  @volatile protected var elem: scala.xml.Elem = _
  private val elementList = List("value", "obj", "member", "arr", "multiple", "argParser","key")

  protected def getTime = Calendar.getInstance.getTime
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
  private def seqCreator(nd: NodeSeq): Seq[(((NodeSeq, NodeSeq), Int), NodeSeq)] = {
    val test: NodeSeq = (nd \\ "unit" \\ "test")
    val result: NodeSeq = (nd \\ "unit" \\ "result")
    val name: NodeSeq = (nd \\ "@name")
    test.zip(result).zipWithIndex.zip(name)
  }

  private def inputTestValidator(ns: NodeSeq, s: String): NodeSeq = {
    ns.filter(i => i.attribute("tag").get.text == s) match {
      case n: NodeSeq => n
      case _ => throw new MatchError("INPUT NOT FOUND IN THE TESTS.\n")
    }
  }

  private def argValidator(p:ParserMap)(s:Symbol)(s2:String):Boolean=if(p.get(s).contains(s2)) true else false

  private def testRes(test: String, expectedResult: String, mode:Parser[Any]): (String, String) = {
    val result = parseAll(mode, test) match {
      case Success(matched, _) => matched
      case Failure(failMsg, _) => System.err.println("TEST: "+test+" FAILURE: " + failMsg)
      case Error(errMsg, _) => System.err.println("ERROR: " + errMsg)
    }
    (result.toString.stripMargin.trim, expectedResult.stripMargin.trim)
  }

  protected def resultValidator(nd:NodeSeq,p:Parser[Any])= {
    for {(elem, index) <- (nd \\ "@name").zipWithIndex} {
      test(elem.text) {
        val (result, expected) = testRes((nd \\ "test") (index).text, (nd \\ "result") (index).text,p)
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

  private def launchTest(nd:NodeSeq,s:String)= {
    logger.info("{LAUNCHING TESTS FOR " + s + "}")
    s match {
      case "value" => org.scalatest.run(new ValueTest(nd))
      case "obj" => org.scalatest.run(new ObjTest(nd))
      case "member" => org.scalatest.run(new MemberTest(nd))
      case "arr" => org.scalatest.run(new ArrTest(nd))
      case "multiple" => org.scalatest.run(new MultTest(nd))
      case "argParser" => org.scalatest.run(new ArgParserTest(nd))
      case "key" => org.scalatest.run(new KeyTest(nd))
    }
  }

  protected def testParamCheck(ns:NodeSeq, p:ParserMap)={
    if(argValidator(p)('TestLauncher)("y")) {
      logger.debug("{LAUNCHING TESTS. TESTS PARAMETER PASSED: "+ getArgument(p,'TestLauncher) +"}")
      try {
        elementList.map(s => (inputTestValidator(ns, s), s)).foreach(y => launchTest(y._1,y._2))
      } catch {
        case ex: Exception => ex.printStackTrace; ex.getMessage
      }
    } else {
      logger.debug("{TESTS SKIPPED. TESTS PARAMETER PASSED: "+getArgument(p,'TestLauncher)+"}")
    }
  }

}

