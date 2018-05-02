package jsonHandler

import scala.annotation.tailrec
import scala.collection.immutable.Map
import scala.util.parsing.combinator.JavaTokenParsers
import scala.util.parsing.input.Reader

/**
  * Created by Pietro.Speri on 09/03/2018.
  */

trait JSONParser extends JavaTokenParsers {

  protected def multiple: Parser[List[Map[Any,Any]]] = rep(value).asInstanceOf[Parser[List[Map[Any,Any]]]]
  protected def value: Parser[Object] = obj | arr | stringLiteral | floatingPointNumber | "null" | "true" | "false"
  protected def obj: Parser[Map[String, Any]] = ("{" ~> repsep(member, ",") <~ "}") ^^ (Map() ++ _)
  protected def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"
  protected def member: Parser[(String, Object)] = stringLiteral ~ ":" ~ value ^^ {case name ~ ":" ~ value => (name, value)}

  //Double conversion
  protected def mapp:Parser[Any] = (element ~ "->").? ~ "Map(" ~ rep(list|mappList|mappEl|mapp|element) ~")" ~ (",").?
  protected def mappList:Parser[Any] = element ~ "->" ~ list ~ (",").?
  protected def mappEl:Parser[Any] = element ~ "->" ~ element ~ (",").?
  protected def list: Parser[Any] = "List(" ~> repsep(element,",") <~ ")"
  protected def element:Parser[Any] = "\""~" "|stringLiteral|floatingPointNumber~"\"" ~ (",").?

  protected def parserLaunch(parser: Parser[Any], reader: Reader[Char]):Option[List[String]] = {
    parseAll(parser, reader) match {
      case Success(matched, _) => Some(matched.asInstanceOf[List[Any]].map(x=>x.toString)) //matched.asInstanceOf[List[Any]].map(x=>println(findKeys(x, "title")))
      case NoSuccess(noSuccMsg, _) => System.err.println("NO SUCCESS MESSAGE: " + noSuccMsg);None
      case Failure(failMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. FAILURE: " + failMsg);None
      case Error(errMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. ERROR: " + errMsg);None
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

