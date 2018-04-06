package testClasses

import jsonHandler.{JSONParser, Util}
import org.scalatest.FunSuite
import scala.xml.NodeSeq

/**
  * Created by Pietro.Speri on 06/04/2018.
  */
class KeyTest(nd: NodeSeq) extends FunSuite with JSONParser with Util {

  def testKeys(m:Map[_,_],expectedResult:Any)(t:Any)= {
    val value = findKeys(m,t)
    (value, expectedResult)
  }

  val testMap = Map("address book" -> Map("name" -> "Speri Pietro", "address" -> Map("street" -> "Street 2", "city" -> "Huntington Beach", "zip" -> "92647"), "phone number" -> List("403827666", "403827667")))
  val l = List((testMap,"address book",List(Map("name" -> "Speri Pietro", "address" -> Map("street" -> "Street 2", "city" -> "Huntington Beach", "zip" -> "92647"), "phone number" -> List("403827666", "403827667")))),
    (testMap,"name",List("Speri Pietro")))

  for(el<-l){
    test(el+" TEST"){
      val (result, expected) = testKeys(el._1,el._3)(el._2)
      try {assert(result == expected)}
    }
  }

}
