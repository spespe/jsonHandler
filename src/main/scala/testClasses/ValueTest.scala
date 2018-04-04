package testClasses

import jsonHandler.{JSONParser, Util}
import org.scalatest.FunSuite
import scala.xml.NodeSeq

/**
  * Created by Pietro.Speri on 05/02/2018.
  */

sealed class ValueTest(nd: NodeSeq) extends FunSuite with JSONParser with Util {

  resultValidator(nd,value)

}

