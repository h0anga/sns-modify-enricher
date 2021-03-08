package sns.lando.modify.enhancer

case class Transaction(instruction: Instruction,
                       traceId: String)

case class Instruction(operatorId:  String,
                       receivedDate: String,
                       order: Order,
                       modifyFeaturesInstruction: ModifyFeaturesInstruction)

case class Order(operatorNotes: String,
                 orderId: String)

case class ModifyFeaturesInstruction(serviceId: String,
                                     features: Features)

case class Features(feature: Seq[Code])

case class Code(code: String)

case class ServiceDetails(serviceId: String, serviceSpecCode: String, directoryNumber: String)

case class EnrichedInstruction(traceId: String,
                               operatorId: String,
                               orderId: String,
                               serviceId: String,
                               directoryNumber: String,
                               features: Seq[String])

