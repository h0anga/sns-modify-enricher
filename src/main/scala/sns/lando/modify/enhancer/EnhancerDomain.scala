package sns.lando.modify.enhancer

//case class VoiceFeatures (modifyVoiceFeaturesMessage: ModifyVoiceFeaturesMessage)

case class ModifyVoiceFeaturesMessage(operatorId: String,
                                      orderId: String,
                                      serviceId: String,
                                      operatorOrderId: String,
                                      features: Features)

case class Features(FEATURE: Seq[Code])

case class Code(CODE: String)

case class ServiceDetails(serviceId: String, serviceSpecCode: String, directoryNumber: String)

case class EnrichedInstruction(operatorId: String,
                               orderId: String,
                               serviceId: String,
                               directoryNumber: String,
                               operatorOrderId: String,
                               features: Seq[String])

