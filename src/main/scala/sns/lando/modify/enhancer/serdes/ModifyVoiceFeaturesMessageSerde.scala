package sns.lando.modify.enhancer.serdes

import java.util
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.{Transaction, VoiceFeaturesParser}

class ModifyVoiceFeaturesMessageSerde extends Serde[Transaction] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def close(): Unit = {

  }

  override def serializer(): Serializer[Transaction] = {
    new ModifyVoiceFeaturesMessageSerializer()
  }

  override def deserializer(): Deserializer[Transaction] = {
    new TransactionDeserializer()
  }
}

class ModifyVoiceFeaturesMessageSerializer extends Serializer[Transaction] {
  val voiceFeaturesParser = new VoiceFeaturesParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def serialize(topic: String, voiceFeatures: Transaction): Array[Byte] = {
    voiceFeaturesParser.parse(voiceFeatures).getBytes
  }

  override def close(): Unit = {

  }
}

class TransactionDeserializer extends Deserializer[Transaction] {
  val voiceFeaturesParser = new VoiceFeaturesParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def deserialize(topic: String, data: Array[Byte]): Transaction = {
    voiceFeaturesParser.parse(new String(data))
  }

  override def close(): Unit = {

  }
}