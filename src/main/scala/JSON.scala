import scala.util.parsing.combinator._
import scala.util.parsing.combinator.JavaTokenParsers
/**
  * Created by Pietro.Speri on 02/02/2018.
  */

class JSON extends JavaTokenParsers {
  def value:Parser[Any] = obj | arr | stringLiteral | floatingPointNumber | "null" | "true" | "false"
  def obj:Parser[Any]
  def arr:Parser[Any]
  def member:Parser[Any]
}
