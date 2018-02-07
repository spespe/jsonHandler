import java.io.InputStream
import org.scalatest.FunSuite
import scala.xml.{NodeSeq, XML, Node}
/**
  * Created by Pietro.Speri on 05/02/2018.
  */
object JSONTest extends FunSuite {
  var input: Option[InputStream] = None
  var elem: scala.xml.Elem = _

  //Test sequence creator method
  def seqCreator(nd:NodeSeq):Seq[((Node,Node),Int)]={
    val test = (nd \\ "test")
    val result = (nd \\ "result")
    test.zip(result).zipWithIndex
  }

  def main(args: Array[String]): Unit = {

    try {
      val input = Some(getClass.getResourceAsStream("/tests.xml"))
      elem = XML.load(input.get)
      val value = (elem \\ "unit").filter(i => i.attribute("tag").get.text == "value")

    } catch {
      case ex:Exception => ex.printStackTrace
        ex.getMessage
    }
  }

}

