package sns.lando.modify.enhancer

import org.apache.kafka.streams.kstream.ValueJoiner

class VoipServicesJoiner extends ValueJoiner[VoiceFeatures, ServiceDetails, ModifyVoiceFeaturesInstruction] {

  override def apply(voiceFeatures: VoiceFeatures, serviceDetails: ServiceDetails): ModifyVoiceFeaturesInstruction = {
    println("Called the Joiner")
    val instruction = ModifyVoiceFeaturesInstruction(voiceFeatures.modifyVoiceFeaturesInstruction.operatorId,
      voiceFeatures.modifyVoiceFeaturesInstruction.orderId,
      voiceFeatures.modifyVoiceFeaturesInstruction.serviceId,
      voiceFeatures.modifyVoiceFeaturesInstruction.operatorOrderId,
      voiceFeatures.modifyVoiceFeaturesInstruction.features)

    instruction
  }
}
