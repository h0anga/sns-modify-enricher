package sns.lando.modify.enhancer

import brave.Tracing
import brave.kafka.streams.KafkaStreamsTracing
import brave.sampler.Sampler
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig, Topology}
import sns.lando.modify.enhancer.serdes.{EnrichedInstructionSerde, ServiceDetailsSerde, TransactionSerde}
import zipkin2.reporter.AsyncReporter
import zipkin2.reporter.kafka11.KafkaSender

import java.time.Duration
import java.util.Properties


class KafkaSetup(private val server: String, private val port: String) {

  private implicit val stringSerde: Serde[String] = Serdes.String()
  private implicit val incomingSerde: Serde[Transaction] = new TransactionSerde()
  private implicit val servicesSerde: Serde[ServiceDetails] = new ServiceDetailsSerde()
  private implicit val outgoingSerde: Serde[EnrichedInstruction] = new EnrichedInstructionSerde()

  private var stream: KafkaStreams = _
  private val bootstrapServers = server + ":" + port
  private val tracing = setupTracing

  private val voiceFeaturesParser = new TransactionParser()
  private val serviceDetailsParser = new ServiceDetailsParser()
  private val enrichedInstructionSerializer = new EnrichedInstructionSerializer()

  val emptyStringPredicate: Predicate[_ >: String, _ >: String] = (_: String, value: String) => {
    value.isEmpty
  }

  val emptyVoiceFeaturesPredicate: Predicate[_ >: String, _ >: Option[Transaction]] = (_: String, value: Option[Transaction]) => {
    value.isEmpty
  }

  val emptyServiceDetailsPredicate: Predicate[_ >: String, _ >: Option[ServiceDetails]] = (_: String, value: Option[ServiceDetails]) => {
    value.isEmpty
  }

  def setupTracing: KafkaStreamsTracing = {
    val sender = KafkaSender.newBuilder.bootstrapServers(bootstrapServers).build
    val reporter = AsyncReporter.builder(sender).build
    val tracing = Tracing.newBuilder.localServiceName("dn-stream-enhancer").sampler(Sampler.ALWAYS_SAMPLE).spanReporter(reporter).build
    KafkaStreamsTracing.create(tracing)
  }

  def start(inputTopicName: String, servicesTopicName: String, outputTopicName: String) = {

    val streamingConfig = {
      val settings = new Properties
      settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "sns-modify-enricher")
      settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
      settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
      settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, outgoingSerde.getClass.getName)
      settings.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, classOf[LogAndContinueExceptionHandler])
      settings
    }
    val topology = build(inputTopicName, servicesTopicName, outputTopicName)
    stream = tracing.kafkaStreams(topology, streamingConfig)
    stream.start()
  }

  def shutDown(): Unit = {
    stream.close()
  }

  def build(inputTopicName: String, servicesTopicName: String, outputTopicName: String): Topology = {
    println("building topology")
    val builder = new StreamsBuilder

    val featuresStream: KStream[String, Transaction] = getTransactionStream(inputTopicName, builder)
    println("Built the featuresStream")
    val servicesStream: KStream[String, ServiceDetails] = getServicesStream(servicesTopicName, builder)
    println("Built the servicesStream")

    val joiner = new VoipServicesJoiner()
    val oneYearWindow = JoinWindows.of(Duration.ofDays(365))
    val transactionSerde = new TransactionSerde()
    val serviceDetailsSerde = new ServiceDetailsSerde()
    val joined: Joined[String, Transaction, ServiceDetails] = Joined.`with`(stringSerde, transactionSerde, serviceDetailsSerde)

    val joinedStream: KStream[String, EnrichedInstruction] =
      featuresStream.join(servicesStream,
        joiner,
        oneYearWindow,
        joined)

    println("Created the Joined stream")

    val keyedOutputStream: KStream[String, EnrichedInstruction] = joinedStream.selectKey((k, v) => v.orderId)

    val outputStream: KStream[String, String] = keyedOutputStream.mapValues(ei =>
      enrichedInstructionSerializer.serialize(ei))
    println("Built the output stream")

    outputStream.to(outputTopicName)

    builder.build()
  }

  def getTransactionStream(transactionTopicName: String, builder: StreamsBuilder): KStream[String, Transaction] = {
    val featuresStream: KStream[String, Transaction] = builder.stream(transactionTopicName, Consumed.`with`(stringSerde, incomingSerde))
    featuresStream.selectKey((k, v) => v.instruction.modifyFeaturesInstruction.serviceId)
  }

  def getServicesStream(servicesTopicName: String, builder: StreamsBuilder): KStream[String, ServiceDetails] = {
    val optionalServicesStream: KStream[String, ServiceDetails] = builder.stream(servicesTopicName, Consumed.`with`(stringSerde, servicesSerde))
    optionalServicesStream.selectKey((k, v) => v.serviceId)
  }
}