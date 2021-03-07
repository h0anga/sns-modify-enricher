package sns.lando.modify.enhancer.serdes

import java.util
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.{InValue, VoiceFeaturesParser}

class ModifyVoiceFeaturesMessageSerde extends Serde[InValue] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def close(): Unit = {

  }

  override def serializer(): Serializer[InValue] = {
    return new ModifyVoiceFeaturesMessageSerializer()
  }

  override def deserializer(): Deserializer[InValue] = {
    return new TransactionDeserializer()
  }
}

class ModifyVoiceFeaturesMessageSerializer extends Serializer[InValue] {
  val voiceFeaturesParser = new VoiceFeaturesParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def serialize(topic: String, voiceFeatures: InValue): Array[Byte] = {
    return voiceFeaturesParser.parse(voiceFeatures).getBytes
  }

  override def close(): Unit = {

  }
}

class TransactionDeserializer extends Deserializer[InValue] {
  val voiceFeaturesParser = new VoiceFeaturesParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def deserialize(topic: String, data: Array[Byte]): InValue = {
    return voiceFeaturesParser.parse(new String(data))
  }

  override def close(): Unit = {

  }
}