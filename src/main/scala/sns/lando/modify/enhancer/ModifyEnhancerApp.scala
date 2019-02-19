package sns.lando.modify.enhancer

import scala.util.Properties

object ModifyEnhancerApp extends App {
  private val kafkabroker: String = Properties.envOrElse("KAFKA_BROKER_SERVER", "localhost")
  private val kafkabrokerPort: String = Properties.envOrElse("KAFKA_BROKER_PORT", "9092")

  private val modifyMessagesTopic = "modify.op.msgs"
  private val enrichedModifyTopic = "enriched.modification.instructions"

  val kafkaSetup = new KafkaSetup(kafkabroker, kafkabrokerPort)
  kafkaSetup.start(modifyMessagesTopic, enrichedModifyTopic)

  sys.ShutdownHookThread {
    kafkaSetup.shutDown()
  }
}
