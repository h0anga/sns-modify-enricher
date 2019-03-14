package sns.lando.modify.enhancer

case class ModifyVoiceFeaturesMessage(OPERATOR_ID: String,
                                      ORDER_ID: String,
                                      SERVICE_ID: String,
                                      OPERATOR_ORDER_ID: String,
                                      FEATURES: Seq[Code])

case class Code(code: String)

case class ServiceDetails(serviceId: String, serviceSpecCode: String, directoryNumber: String)

case class EnrichedInstruction(operatorId: String,
                               orderId: String,
                               serviceId: String,
                               directoryNumber: String,
                               operatorOrderId: String,
                               features: Seq[String])

