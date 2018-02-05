
import scala.util.parsing.combinator.JavaTokenParsers
/**
  * Created by Pietro.Speri on 02/02/2018.
  */

class JSON extends JavaTokenParsers {
  def value:Parser[Any] = (obj | arr | stringLiteral | floatingPointNumber ^^ (_.toDouble) |
    "null" ^^ {x => null} |
    "true" ^^ {x => true} |
    "false" ^^ {x => false})
  def obj:Parser[Map[String,Any]] = "{"~>repsep(member,",")<~"}" ^^ (Map() ++ _)
  def arr:Parser[List[Any]] = "["~>repsep(value, ",")<~"]"
  def member:Parser[(String,Any)] = stringLiteral~":"~value ^^ {case name~":"~value => (name,value)}
}
