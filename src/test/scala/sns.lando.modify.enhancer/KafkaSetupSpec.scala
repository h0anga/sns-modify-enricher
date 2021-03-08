package sns.lando.modify.enhancer

import java.util.{Properties, UUID}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler
import org.apache.kafka.streams.test.ConsumerRecordFactory
import org.apache.kafka.streams.{StreamsConfig, TopologyTestDriver}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class KafkaSetupSpec extends AnyFlatSpec with Matchers {
  private val kafkaApplicationId = "sns-modify-enricher"
  private val serverName = "serverName"
  private val portNumber = "portNumber"

  private val inputTopic = "topic-in"
  private val servicesTopic = "topic-services"
  private val outputTopic = "topic-out"

  private val kafkaMessageInKey = "key"
  private val kafkaServicesKey = "someOtherKey"

  private val streamingConfig = {
    val settings = new Properties
    settings.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaApplicationId)
    settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, serverName + ":" + portNumber)
    settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    settings.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, classOf[LogAndContinueExceptionHandler])
    settings
  }

  private val orderId = UUID.randomUUID().toString

  private val kafkaMessageInValue = s"""{"transaction":{"operatorId":"sky","receivedDate":"2018-11-15T10:29:07","instruction":{"order":{"operatorNotes":"Test: notes","orderId":"$orderId"},"modifyFeaturesInstruction":{"serviceId":"31642339","features":{"feature":[{"code":"CallerDisplay"},{"code":"RingBack"},{"code":"ChooseToRefuse"}]}}}},"traceId":"792dd3058e223dbb"}"""
  private val kafkaServicesValue = s"""{"serviceId":31642339,"serviceSpecCode":"VoipService","directoryNumber":"01202000095"}"""

  private val expectedOutput =
    s"""
      |{"modifyVoiceFeaturesInstruction":{"operatorId":"sky","orderId":"$orderId","serviceId":"31642339","operatorOrderId":"SogeaVoipModify_YHUORO","features":["CallerDisplay","RingBack","ChooseToRefuse"]}}
    """.stripMargin

  private def createTopologyToTest = {
    val kafkaSetup = new KafkaSetup(serverName, portNumber)
    val topology = kafkaSetup.build(inputTopic, servicesTopic, outputTopic)
    topology
  }

  it should "test a stream" in {
    val topology = createTopologyToTest
    val topologyTestDriver = new TopologyTestDriver(topology, streamingConfig)

    val keySerde: Serde[String] = Serdes.String
    val valueSerde: Serde[String] = Serdes.String

    val servicesConsumerRecordFactory: ConsumerRecordFactory[String, String] =
      new ConsumerRecordFactory[String, String](servicesTopic, keySerde.serializer(), valueSerde.serializer())
    val servicesKafkaRecord: ConsumerRecord[Array[Byte], Array[Byte]] = servicesConsumerRecordFactory.create(servicesTopic, kafkaServicesKey, kafkaServicesValue)
    val inputConsumerRecordFactory: ConsumerRecordFactory[String, String] = new ConsumerRecordFactory[String, String](inputTopic, keySerde.serializer(), valueSerde.serializer())
    val inputKafkaRecord: ConsumerRecord[Array[Byte], Array[Byte]] = inputConsumerRecordFactory.create(inputTopic, kafkaMessageInKey, kafkaMessageInValue)
    topologyTestDriver.pipeInput(inputKafkaRecord)

    val outputKafkaRecord: ProducerRecord[String, String] = topologyTestDriver.readOutput(
      outputTopic, keySerde.deserializer(),
      valueSerde.deserializer())
    val outputValue = outputKafkaRecord.value()

    outputValue.trim shouldEqual (expectedOutput.trim)
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
