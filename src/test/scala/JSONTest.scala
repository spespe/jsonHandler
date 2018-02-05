import java.io.InputStream

import org.scalatest.FunSuite

import scala.xml.XML
/**
  * Created by Pietro.Speri on 05/02/2018.
  */
object JSONTest extends FunSuite {
  var input: Option[InputStream] = None
  var elem: scala.xml.Elem = _

  try {
    val inputFile = Some(getClass.getResourceAsStream("/tests.xml"))
    elem = XML.load(inputFile.get)
    val value = (elem \\ "value").filter(i=>i.attribute("tag").get.text=="value")
  }

}

