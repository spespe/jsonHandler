package jsonHandler

import java.util.Calendar

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.FunSuite
import testClasses._

import scala.annotation.tailrec
import scala.collection.immutable.Map
import scala.util.parsing.input.Reader
import scala.xml.{NodeSeq, XML}

/**
  * Created by Pietro.Speri on 07/03/2018.
  **/

trait UtilT extends FunSuite with ArgumentsParser with JSONParser with LazyLogging {

  @volatile protected var elem: scala.xml.Elem = _
  private val elementList = List("value", "obj", "member", "arr", "multiple", "argParser")

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

  protected def parserLaunch(parser: Parser[Any], reader: Reader[Char]) = {
    parseAll(parser, reader) match {
      case Success(matched, _) => mappingKeyValues(matched.asInstanceOf[List[Map[String,Any]]])
      case Failure(failMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. FAILURE: " + failMsg)
      case Error(errMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. ERROR: " + errMsg)
    }
  }

  def mappingKeyValues(m:List[Map[String,Any]]):Any = m match {
    case List() =>
    case x::xs => x+"\nSEPARATOR JSONS\n"+mappingKeyValues(xs)
    //case m2:Map[String,Map[String,Any]] => mappingKeyValues(m2.flatMap{case(k,v) if(v.isInstanceOf[Map[_,_]]) => Map(k -> v.asInstanceOf[Map[String,Any]])})
    //case m2:Map[String,List[Any]] => //m2.values
    //case m2:Map[String,String] => //m2.values
  }

  def findKeys[T](c: _, key: T): List[_] = {
    @tailrec
    def findKeysImp(c: _, key: T, acc: List[_]):List[_] = {
      c match {
        case m: Map[T, _] => {
          val valueOpt = m.get(key)
          if (valueOpt.isDefined)
            valueOpt.get :: acc
          else
            findKeysImp(m.values, key, acc)
        }
        case Some(v) => findKeysImp(v, key, acc)
        case tr: Traversable[_] => findKeysTrav(tr, key, acc)
        case _ => acc
      }
    }

    @tailrec
    def findKeysTrav(tr: Traversable[_], key: T, acc: List[_]): List[_] = {
      if (tr.isEmpty) acc
      else {
        val headAcc = findKeysImp(tr.head, key, acc)
        findKeysTrav(tr.tail, key, headAcc)
      }
    }
    findKeysImp(c, key, List.empty[_])
  }
}
