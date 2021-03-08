package sns.lando.modify.enhancer

import org.apache.kafka.streams.kstream.ValueJoiner

class VoipServicesJoiner extends ValueJoiner[Transaction, ServiceDetails, EnrichedInstruction] {

  override def apply(transaction: Transaction, serviceDetails: ServiceDetails): EnrichedInstruction = {
    println("Called the Joiner")
    val features: Seq[Code] = transaction.instruction.modifyFeaturesInstruction.features.feature
    val codes: Seq[String] = features.map(f => f.code)

    val instruction = EnrichedInstruction(
      transaction.traceId,
      transaction.instruction.operatorId,
      transaction.instruction.order.orderId,
      transaction.instruction.modifyFeaturesInstruction.serviceId,
      serviceDetails.directoryNumber,
      codes)

    instruction
  }
}
