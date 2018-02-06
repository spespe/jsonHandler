/**
  * Created by Pietro.Speri on 05/02/2018.
  */
import org.scalatest.FunSuite
import scala.language._
import scala.xml.NodeSeq

class valueTest(nd:NodeSeq) extends FunSuite {
  for{
    (el,idx)<-(nd \\ "@name").zipWithIndex
  }{
    test(el.text){
      //
    }
  }
}
