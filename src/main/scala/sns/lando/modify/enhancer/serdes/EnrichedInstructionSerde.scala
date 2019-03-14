package sns.lando.modify.enhancer.serdes

import java.util

import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.{EnrichedInstruction, EnrichedInstructionParser, ModifyVoiceFeaturesMessage, VoiceFeaturesParser}

class EnrichedInstructionSerde extends Serde[EnrichedInstruction] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def close(): Unit = {

  }

  override def serializer(): Serializer[EnrichedInstruction] = {
    return new EnrichedInstructionSerializer()
  }

  override def deserializer(): Deserializer[EnrichedInstruction] = {
    return new EnrichedInstructionDeserializer()
  }
}

class EnrichedInstructionSerializer extends Serializer[EnrichedInstruction] {
  val enrichedInstructionParser = new EnrichedInstructionParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def serialize(topic: String, enrichedInstruction: EnrichedInstruction): Array[Byte] = {
    return enrichedInstructionParser.parse(enrichedInstruction).getBytes
  }

  override def close(): Unit = {

  }
}

class EnrichedInstructionDeserializer extends Deserializer[EnrichedInstruction] {
  val enrichedInstructionParser = new EnrichedInstructionParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def deserialize(topic: String, data: Array[Byte]): EnrichedInstruction = {
    return enrichedInstructionParser.parse(new String(data))
  }

  override def close(): Unit = {

  }
}