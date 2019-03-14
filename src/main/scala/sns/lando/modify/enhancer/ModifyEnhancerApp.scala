package sns.lando.modify.enhancer

import scala.util.Properties

object ModifyEnhancerApp extends App {
  private val kafkabroker: String = Properties.envOrElse("KAFKA_BROKER_SERVER", "localhost")
  private val kafkabrokerPort: String = Properties.envOrElse("KAFKA_BROKER_PORT", "9092")

  private val modifyMessagesTopic = "RAW_VOIP_INSTRUCTIONS"
  private val servicesTopic = "services"
  private val enrichedModifyTopic = "enriched.modification.instructions.with.service"

  val kafkaSetup = new KafkaSetup(kafkabroker, kafkabrokerPort)
  kafkaSetup.start(modifyMessagesTopic, servicesTopic, enrichedModifyTopic)

  sys.ShutdownHookThread {
    kafkaSetup.shutDown()
  }
}
