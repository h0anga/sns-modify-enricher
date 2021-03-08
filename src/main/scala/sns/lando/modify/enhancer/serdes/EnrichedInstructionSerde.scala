package sns.lando.modify.enhancer.serdes

import java.util

import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.{EnrichedInstruction, EnrichedInstructionParser}

class EnrichedInstructionSerde extends Serde[EnrichedInstruction] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = { }

  override def close(): Unit = {}

  override def serializer(): Serializer[EnrichedInstruction] = new EnrichedInstructionSerializer()

  override def deserializer(): Deserializer[EnrichedInstruction] = new EnrichedInstructionDeserializer()
}

class EnrichedInstructionSerializer extends Serializer[EnrichedInstruction] {
  val enrichedInstructionParser = new EnrichedInstructionParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def serialize(topic: String, enrichedInstruction: EnrichedInstruction): Array[Byte] = enrichedInstructionParser.parse(enrichedInstruction).getBytes

  override def close(): Unit = {}
}

class EnrichedInstructionDeserializer extends Deserializer[EnrichedInstruction] {
  val enrichedInstructionParser = new EnrichedInstructionParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def deserialize(topic: String, data: Array[Byte]): EnrichedInstruction = enrichedInstructionParser.parse(new String(data))

  override def close(): Unit = {}
}