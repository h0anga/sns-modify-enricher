package sns.lando.modify.enhancer

import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization._
import org.apache.kafka.streams._
import org.apache.kafka.streams.scala.kstream.Consumed

class KafkaSetup(private val server: String, private val port: String) {

//  private val KafkaStringSerializer = "org.apache.kafka.common.serialization.StringSerializer"
//  private val KafkaStringDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"

  private implicit val stringSerde: Serde[String] = Serdes.String()

  private var stream: KafkaStreams = _

  def start(inputTopicName: String, outputTopicName: String) = {

    val bootstrapServers = server + ":" + port

    val streamingConfig = {
      val settings = new Properties
      settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "sns-knitware-converter")
      settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
      settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
      settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
      settings
    }
    val topology = build(inputTopicName, outputTopicName)
    stream = new KafkaStreams(topology, streamingConfig)
    stream.start()
  }

  def shutDown(): Unit = {
    stream.close()
  }

  def build(inputTopicName: String, outputTopicName: String): Topology = {
    val builder = new StreamsBuilder

    builder.stream(inputTopicName, Consumed.`with`(stringSerde, stringSerde))
      .mapValues(line => new KnitwareConverter().getXmlFor(line))
      .to(outputTopicName)

    return builder.build()
  }
}