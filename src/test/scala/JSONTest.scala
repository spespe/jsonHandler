import java.io.InputStream

import org.scalatest.FunSuite

import scala.xml.{NodeSeq, XML}
/**
  * Created by Pietro.Speri on 05/02/2018.
  */
object JSONTest extends FunSuite {
  var input: Option[InputStream] = None
  var elem: scala.xml.Elem = _

  def main(args: Array[String]): Unit = {
    try {
      val inputFile = Some(getClass.getResourceAsStream("/tests.xml"))
      val jsonFile = Some(getClass.getResourceAsStream("/jsonExample.json"))
      elem = XML.load(inputFile.get)
      val value = (elem \\ "unit").filter(i => i.attribute("tag").get.text == "value").zipWithIndex

      def seqCreator(nd:NodeSeq,par:String)(input:Any,expRes:Any):Seq[((Any,Any),Int)]={
        val test = (nd \\ "test" \\ par)
        val result = (nd \\ "result" \\ par)
        test.zip(result).zipWithIndex
      }

      //run
      (elem \\ "unit" \\ "test").foreach(println)
      //value.filter(_.contains("value")).foreach(println)
//      run(new valueTest(value)) //to be created
    }
  }

}

