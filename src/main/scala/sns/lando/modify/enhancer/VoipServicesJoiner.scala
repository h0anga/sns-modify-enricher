package sns.lando.modify.enhancer

import org.apache.kafka.streams.kstream.ValueJoiner

class VoipServicesJoiner extends ValueJoiner[ModifyVoiceFeaturesMessage, ServiceDetails, EnrichedInstruction] {

  override def apply(voiceFeatures: ModifyVoiceFeaturesMessage, serviceDetails: ServiceDetails): EnrichedInstruction = {
    println("Called the Joiner")
    val features: Seq[Code] = voiceFeatures.FEATURES.FEATURE

    val instruction = EnrichedInstruction(voiceFeatures.OPERATOR_ID,
      voiceFeatures.ORDER_ID,
      voiceFeatures.SERVICE_ID,
      serviceDetails.directoryNumber,
      voiceFeatures.OPERATOR_ORDER_ID,
      features.map(_.CODE))

    instruction
  }
}
