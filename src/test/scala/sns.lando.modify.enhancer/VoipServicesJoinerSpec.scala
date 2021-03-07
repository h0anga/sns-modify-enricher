package sns.lando.modify.enhancer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class VoipServicesJoinerSpec extends AnyFlatSpec with Matchers {
  private val joiner = new VoipServicesJoiner

  private val orderId = UUID.randomUUID().toString
  private val serviceId = UUID.randomUUID().toString
  private val directoryNumber = UUID.randomUUID().toString
  private val inValue = InValue(Transaction("sky","2018-11-15T10:29:07",Instruction(Order("Test: notes",s"$orderId"),ModifyFeaturesInstruction(s"$serviceId",Features(List(Code("CallerDisplay"), Code("RingBack"), Code("ChooseToRefuse")))))),"792dd3058e223dbb")
  private val serviceDetails = ServiceDetails(s"$serviceId","VoipService",s"$directoryNumber")
  private val instruction = EnrichedInstruction("792dd3058e223dbb", "sky",s"$orderId",s"$serviceId",s"$directoryNumber", List("CallerDisplay", "RingBack", "ChooseToRefuse"))

  it should "test instruction merging" in {
    org.json4s.native.JsonMethods
    joiner.apply(inValue, serviceDetails) shouldEqual instruction
  }
}
