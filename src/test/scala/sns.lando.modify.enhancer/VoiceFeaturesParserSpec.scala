package sns.lando.modify.enhancer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class VoiceFeaturesParserSpec extends AnyFlatSpec with Matchers {
  private val parser = new VoiceFeaturesParser

  private val orderId = UUID.randomUUID().toString
  private val inJson = s"""{"transaction":{"operatorId":"sky","receivedDate":"2018-11-15T10:29:07","instruction":{"order":{"operatorNotes":"Test: notes","orderId":"$orderId"},"modifyFeaturesInstruction":{"serviceId":"31642339","features":{"feature":[{"code":"CallerDisplay"},{"code":"RingBack"},{"code":"ChooseToRefuse"}]}}}},"traceId":"792dd3058e223dbb"}"""
  private val inValue = InValue(Transaction("sky","2018-11-15T10:29:07",Instruction(Order("Test: notes",s"$orderId"),ModifyFeaturesInstruction("31642339",Features(List(Code("CallerDisplay"), Code("RingBack"), Code("ChooseToRefuse")))))),"792dd3058e223dbb")

  it should "test inValue parsing" in {
    org.json4s.native.JsonMethods
    parser.parse(inValue) shouldEqual inJson
  }

  it should "test inJson parsing" in {
    parser.parse(inJson) shouldEqual inValue
  }
}