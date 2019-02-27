package sns.lando.modify.enhancer

import org.apache.kafka.streams.kstream.ValueJoiner

class VoipServicesJoiner extends ValueJoiner[VoiceFeatures, ServiceDetails, EnrichedInstruction] {

  override def apply(voiceFeatures: VoiceFeatures, serviceDetails: ServiceDetails): EnrichedInstruction = {
    println("Called the Joiner")
    val instruction = EnrichedInstruction(voiceFeatures.modifyVoiceFeaturesInstruction.operatorId,
      voiceFeatures.modifyVoiceFeaturesInstruction.orderId,
      voiceFeatures.modifyVoiceFeaturesInstruction.serviceId,
      serviceDetails.directoryNumber,
      voiceFeatures.modifyVoiceFeaturesInstruction.operatorOrderId,
      voiceFeatures.modifyVoiceFeaturesInstruction.features)

    instruction
  }
}
