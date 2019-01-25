package sns.lando.modify.enhancer

import java.util.{Properties, UUID}

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler
import org.apache.kafka.streams.test.ConsumerRecordFactory
import org.apache.kafka.streams.{StreamsConfig, TopologyTestDriver}
import org.scalatest._

class KafkaSetupSpec extends FlatSpec with Matchers {
  private val kafkaApplicationId = "sns-modify-enricher"
  private val serverName = "serverName"
  private val portNumber = "portNumber"

  private val inputTopic = "topic-in"
  private val outputTopic = "topic-out"

  private val kafkaMessageInKey = "key"
  private val streamingConfig = {
    val settings = new Properties
    settings.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaApplicationId)
    settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, serverName + ":" + portNumber)
    settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    settings.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, classOf[LogAndContinueExceptionHandler])
    settings
  }

  private val netstreamCorrelationId = UUID.randomUUID().toString

  private val kafkaMessageInValue =
    s"""
      |{"netstreamCorrelationId":"${netstreamCorrelationId}"}
    """.stripMargin

  private val expectedOutput =
    s"""
      |{"netstreamCorrelationId":"${netstreamCorrelationId}"}
    """.stripMargin

  private def createTopologyToTest = {
    val kafkaSetup = new KafkaSetup(serverName, portNumber)
    val topology = kafkaSetup.build(inputTopic, outputTopic)
    topology
  }

  it should "test a stream" in {
    val topology = createTopologyToTest
    val topologyTestDriver = new TopologyTestDriver(topology, streamingConfig)

    val keySerde: Serde[String] = Serdes.String
    val valueSerde: Serde[String] = Serdes.String

    val consumerRecordFactory: ConsumerRecordFactory[String, String] = new ConsumerRecordFactory[String, String](inputTopic, keySerde.serializer(), valueSerde.serializer())
    val inputKafkaRecord: ConsumerRecord[Array[Byte], Array[Byte]] = consumerRecordFactory.create(inputTopic, kafkaMessageInKey, kafkaMessageInValue)
    topologyTestDriver.pipeInput(inputKafkaRecord)

    val outputKafkaRecord: ProducerRecord[String, String] = topologyTestDriver.readOutput(outputTopic, keySerde.deserializer(), valueSerde.deserializer())
    val outputValue = outputKafkaRecord.value()

    outputValue shouldEqual (expectedOutput)
  }

  it should "spit out poison pills" in {
    val topology = createTopologyToTest
    val topologyTestDriver = new TopologyTestDriver(topology, streamingConfig)

    val keySerde: Serde[String] = Serdes.String
    val valueSerde: Serde[String] = Serdes.String

    val consumerRecordFactory: ConsumerRecordFactory[String, String] = new ConsumerRecordFactory[String, String](inputTopic, keySerde.serializer(), valueSerde.serializer())
    val inputKafkaRecord: ConsumerRecord[Array[Byte], Array[Byte]] = consumerRecordFactory.create(inputTopic, kafkaMessageInKey, "poison!")
    topologyTestDriver.pipeInput(inputKafkaRecord)

    val outputKafkaRecord: ProducerRecord[String, String] = topologyTestDriver.readOutput(outputTopic, keySerde.deserializer(), valueSerde.deserializer())
    if (outputKafkaRecord != null)
      fail("Got a message from a poison pill")
  }

}
