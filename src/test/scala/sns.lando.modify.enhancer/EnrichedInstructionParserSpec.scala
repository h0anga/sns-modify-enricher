package sns.lando.modify.enhancer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class EnrichedInstructionParserSpec extends AnyFlatSpec with Matchers {
  private val parser = new EnrichedInstructionParser

  private val orderId = UUID.randomUUID().toString
  private val serviceId = UUID.randomUUID().toString
  private val directoryNumber = UUID.randomUUID().toString
  private val enrichedInstructionJson = s"""{"traceId":"792dd3058e223dbb","operatorId":"sky","orderId":"$orderId","serviceId":"$serviceId","directoryNumber":"$directoryNumber","features":["CallerDisplay","RingBack","ChooseToRefuse"]}"""
  private val enrichedInstruction = EnrichedInstruction("792dd3058e223dbb", "sky", s"$orderId", s"$serviceId", s"$directoryNumber", List("CallerDisplay", "RingBack", "ChooseToRefuse"))

  it should "test enrichedInstruction parsing" in {
    parser.parse(enrichedInstruction) shouldEqual enrichedInstructionJson
  }

  it should "test enrichedInstructionJson parsing" in {
    parser.parse(enrichedInstructionJson) shouldEqual enrichedInstruction
  }
}
