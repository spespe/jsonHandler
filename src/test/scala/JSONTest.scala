import java.io.InputStream
import java.util.Calendar
import org.scalatest.FunSuite
import scala.xml.{Node, NodeSeq, XML}
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by Pietro.Speri on 05/02/2018.
  */

object JSONTest extends FunSuite with LazyLogging {
  var input: Option[InputStream] = None
  var elem: scala.xml.Elem = _

  //Test sequence creator method
  def seqCreator(nd:NodeSeq):Seq[(((Node,Node),Int),Node)]={
    val test = (nd \\ "test")
    val result = (nd \\ "result")
    val name = (nd \\ "@name")
    test.zip(result).zipWithIndex.zip(name)
  }

  val input = Some(getClass.getResourceAsStream("/tests.xml"))
  elem = XML.load(input.get)

  def inputTestValidator(s:String):Option[NodeSeq]={
    (elem \\ "unit").filter(i => i.attribute("tag").get.text == s) match {
      case n:NodeSeq => Some(n)
      case _ => println("INPUT NOT FOUND IN THE TESTS.\n"); None
    }
  }

  def main(args: Array[String]): Unit = {
    try {

      val value = inputTestValidator("value").get
      val obj = inputTestValidator("obj").get
      val member = inputTestValidator("member").get
      val arr = inputTestValidator("arr").get

      logger.info("{DATETIME}")
      println(Calendar.getInstance.getTime)

      logger.debug("{LAUNCHING TESTS FOR VALUE}")
      run(new valueTest(seqCreator(value)))
      logger.debug("{LAUNCHING TESTS FOR OBJ}")
      run(new valueTest(seqCreator(obj)))
      logger.debug("{LAUNCHING TESTS FOR MEMBER}")
      run(new valueTest(seqCreator(member)))
      logger.debug("{LAUNCHING TESTS FOR ARR}")
      run(new valueTest(seqCreator(arr)))

    } catch {
      case ex:Exception => ex.printStackTrace; ex.getMessage
    }
  }

}

