package jsonHandler

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by Pietro.Speri on 09/03/2018.
  */

trait JSONParser extends JavaTokenParsers {
  protected def value: Parser[Any] = obj | arr | stringLiteral | floatingPointNumber ^^ (_.toDouble) |
    "null" ^^  { _ => null }  | "true" ^^ { _ => true} | "false" ^^ { _ => false }

  protected def obj: Parser[Map[String, Any]] = "{" ~> repsep(member, ",") <~ "}" ^^ (Map() ++ _)

  protected def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

  protected def member: Parser[(String, Any)] = stringLiteral ~ ":" ~ value ^^ { case name ~ ":" ~ value => (name, value) }
}


