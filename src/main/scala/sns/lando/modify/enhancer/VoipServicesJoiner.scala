package sns.lando.modify.enhancer

import org.apache.kafka.streams.kstream.ValueJoiner

class VoipServicesJoiner extends ValueJoiner[ModifyVoiceFeaturesMessage, ServiceDetails, EnrichedInstruction] {

  override def apply(voiceFeatures: ModifyVoiceFeaturesMessage, serviceDetails: ServiceDetails): EnrichedInstruction = {
    println("Called the Joiner")
    val features: Seq[Code] = voiceFeatures.features.FEATURE

    val instruction = EnrichedInstruction(voiceFeatures.operatorId,
      voiceFeatures.orderId,
      voiceFeatures.serviceId,
      serviceDetails.directoryNumber,
      voiceFeatures.operatorOrderId,
      features.map(_.CODE))

    instruction
  }
}
