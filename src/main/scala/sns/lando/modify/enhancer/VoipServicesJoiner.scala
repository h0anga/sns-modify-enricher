package sns.lando.modify.enhancer

import org.apache.kafka.streams.kstream.ValueJoiner

class VoipServicesJoiner extends ValueJoiner[InValue, ServiceDetails, EnrichedInstruction] {

  override def apply(inValue: InValue, serviceDetails: ServiceDetails): EnrichedInstruction = {
    println("Called the Joiner")
    val features: Seq[Code] = inValue.transaction.instruction.modifyFeaturesInstruction.features.feature
    val codes: Seq[String] = features.map(f => f.code)

    val instruction = EnrichedInstruction(
      inValue.traceId,
      inValue.transaction.operatorId,
      inValue.transaction.instruction.order.orderId,
      inValue.transaction.instruction.modifyFeaturesInstruction.serviceId,
      serviceDetails.directoryNumber,
      codes)

    instruction
  }
}
