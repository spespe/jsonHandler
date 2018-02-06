/**
  * Created by Pietro.Speri on 05/02/2018.
  */
import jsonHandler.parse
import org.scalatest.FunSuite
import scala.language._
import scala.xml.NodeSeq

class valueTest(nd:NodeSeq) extends FunSuite {

  var res,timestmp=""

  def testing(input:String)()={
    //to be finished
    val res = parse(value,reader)
      res
  }

  for(el<-(nd \\ "@name")){
    test(el.text){
      // assert
    }
  }
}
