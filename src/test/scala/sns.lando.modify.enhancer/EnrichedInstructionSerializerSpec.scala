package sns.lando.modify.enhancer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class EnrichedInstructionSerializerSpec extends AnyFlatSpec with Matchers {
  private val serializer = new EnrichedInstructionSerializer

  private val orderId = UUID.randomUUID().toString
  private val enrichedInstructionSerialized = s"""{"enrichedInstruction":{"traceId":"792dd3058e223dbb","operatorId":"sky","orderId":"$orderId","serviceId":"flhtd","directoryNumber":"0707790432","features":["CallerDisplay","RingBack","ChooseToRefuse"]}}"""
  private val enrichedInstruction = EnrichedInstruction("792dd3058e223dbb", "sky", s"$orderId", "flhtd", "0707790432", List("CallerDisplay", "RingBack", "ChooseToRefuse"))

  it should "test enrichedInstruction serializing" in {
    serializer.serialize(enrichedInstruction) shouldEqual enrichedInstructionSerialized
  }

}
