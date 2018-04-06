package jsonHandler

import scala.annotation.tailrec
import scala.collection.immutable.Map
import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Reader

/**
  * Created by Pietro.Speri on 09/03/2018.
  */

trait JSONParser extends JavaTokenParsers {

  protected def multiple: Parser[Any] = rep(value)

  protected def value: Parser[Any] = obj | arr | stringLiteral | floatingPointNumber ^^ (_.toDouble) |
  "null" ^^  { _ => null }  | "true" ^^ { _ => true} | "false" ^^ { _ => false }

  protected def obj: Parser[Map[String, Any]] = "{" ~> repsep(member, ",") <~ "}" ^^ (Map() ++ _)

  protected def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

  protected def member: Parser[(String, Any)] = stringLiteral ~ ":" ~ value ^^ { case name ~ ":" ~ value => (name, value) }

  //Double conversion
  protected def elementMap: Parser[Any] = element|list|map
  protected def map:Parser[Map[Any,Any]] = "Map(" ~> repsep(element, "->") <~ "," ^^ { Map() ++ _}
  protected def list: Parser[List[Any]] = "List(" ~> repsep(element,",") <~ ")" ^^ (_.toString.split(",").toList)
  protected def element:Parser[Any] = "\""~" "|stringLiteral|floatingPointNumber~"\""

  protected def parserLaunch(parser: Parser[Any], reader: Reader[Char]) = {
    parseAll(parser, reader) match {
      case Success(matched:Traversable[_], _) => matched.foreach(println)//val m:List[Map[String,Any]]= matched.map(x=>x.asInstanceOf[Map[Any,Any]])(collection.breakOut)
      case NoSuccess(noSuccMsg, _) => System.err.println("NO SUCCESS MESSAGE: " + noSuccMsg);
      case Failure(failMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. FAILURE: " + failMsg);
      case Error(errMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. ERROR: " + errMsg);
    }
  }

  def findKeys(c: Any, key: Any): List[Any] = {
    @tailrec
    def findKeysTrav(tr: Traversable[Any], key: Any, acc: List[Any] ): List[Any] = {
      if (tr.isEmpty) acc
      else {
        val headAcc = findKeysImp(tr.head, key, acc)
        findKeysTrav(tr.tail, key, headAcc)
      }
    }

    @tailrec
    def findKeysImp(c: Any, key: Any, acc: List[Any]):List[Any] = {
      c match {
        case m: Map[Any, Any] => {
          val valueOpt = m.get(key)
          if (valueOpt.isDefined)
            valueOpt.get :: acc
          else
            findKeysImp(m.values, key, acc)
        }
        case Some(s) => findKeysImp(s, key, acc)
        case tr: Traversable[Any] => findKeysTrav(tr, key, acc)
        case _ => acc
      }
    }

    findKeysImp(c, key, List.empty[Any])
  }
}



