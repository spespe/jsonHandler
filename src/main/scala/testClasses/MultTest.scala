package testClasses

import jsonHandler.{JSONParser, UtilT}
import org.scalatest.FunSuite
import scala.xml.NodeSeq

/**
  * Created by Pietro.Speri on 07/02/2018.
  */
sealed class MultTest(nd: NodeSeq) extends FunSuite with JSONParser with UtilT {

  resultValidator(nd,multiple)

}
