package sns.lando.modify.enhancer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class TransactionParserSpec extends AnyFlatSpec with Matchers {
  private val parser = new TransactionParser

  private val orderId = UUID.randomUUID().toString
  private val inJson = s"""{"instruction":{"operatorId":"sky","receivedDate":"2018-11-15T10:29:07","order":{"operatorNotes":"Test: notes","orderId":"$orderId"},"modifyFeaturesInstruction":{"serviceId":"31642339","features":{"feature":[{"code":"CallerDisplay"},{"code":"RingBack"},{"code":"ChooseToRefuse"}]}}},"traceId":"792dd3058e223dbb"}"""
  private val transaction = Transaction(Instruction("sky","2018-11-15T10:29:07",Order("Test: notes",s"$orderId"),ModifyFeaturesInstruction("31642339",Features(List(Code("CallerDisplay"), Code("RingBack"), Code("ChooseToRefuse"))))),"792dd3058e223dbb")

  it should "test inValue parsing" in {
    org.json4s.native.JsonMethods
    parser.parse(transaction) shouldEqual inJson
  }

  it should "test inJson parsing" in {
    parser.parse(inJson) shouldEqual transaction
  }
}