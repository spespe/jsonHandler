package testClasses

import jsonHandler.{JSONParser, Util}
import org.scalatest.FunSuite
import scala.xml.NodeSeq

/**
  * Created by Pietro.Speri on 07/02/2018.
  */
sealed class MemberTest(nd: NodeSeq) extends FunSuite with JSONParser with Util {

  resultValidator(nd,member)

}
