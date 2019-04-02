package sns.lando.modify.enhancer

import org.apache.kafka.streams.kstream.ValueJoiner

class VoipServicesJoiner extends ValueJoiner[ModifyVoiceFeaturesMessage, ServiceDetails, EnrichedInstruction] {

  override def apply(voiceFeatures: ModifyVoiceFeaturesMessage, serviceDetails: ServiceDetails): EnrichedInstruction = {
    println("Called the Joiner")
    val features: Seq[Code] = voiceFeatures.FEATURES
    val codes: Seq[String] = features.map(f => f.code)

    val instruction = EnrichedInstruction(
      voiceFeatures.TRACE_ID,
      voiceFeatures.OPERATOR_ID,
      voiceFeatures.ORDER_ID,
      voiceFeatures.SERVICE_ID,
      serviceDetails.directoryNumber,
      voiceFeatures.OPERATOR_ORDER_ID,
      codes)

    instruction
  }
}
