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


  protected def parserLaunch(parser: Parser[Any], reader: Reader[Char]) = {
    parseAll(parser, reader) match {
      case Success(matched, _) => findKeys(matched.asInstanceOf[Map[_,Any]],"")
      case Failure(failMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. FAILURE: " + failMsg)
      case Error(errMsg, _) => System.err.println("PLEASE CHECK THE INPUT JSON FILE. ERROR: " + errMsg)
    }
  }

  //To find all the values for a defined key
  def findKeys[T](c: Map[_,Any], key: T): List[Any] = {
    @tailrec
    def findKeysTrav(tr: Traversable[_], key: T, acc: List[_]): List[_] = {
      if (tr.isEmpty) acc
      else {
        val headAcc = findKeysImp(tr.head, key, acc)
        findKeysTrav(tr.tail, key, headAcc)
      }
    }

    @tailrec
    def findKeysImp(c: Any, key: T, acc: List[Any]):List[_] = {
      c match {
        case m: Map[T, Any] => {
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

    findKeysImp(c, key, List.empty[Any])
  }
}

