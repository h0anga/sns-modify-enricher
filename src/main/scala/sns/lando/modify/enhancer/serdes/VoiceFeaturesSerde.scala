package sns.lando.modify.enhancer.serdes

import java.util

import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.{VoiceFeatures, VoiceFeaturesParser}

class VoiceFeaturesSerde extends Serde[VoiceFeatures] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def close(): Unit = {

  }

  override def serializer(): Serializer[VoiceFeatures] = {
    return new VoiceFeaturesSerializer()
  }

  override def deserializer(): Deserializer[VoiceFeatures] = {
    return new VoiceFeaturesDeserializer()
  }
}

class VoiceFeaturesSerializer extends Serializer[VoiceFeatures] {
  val voiceFeaturesParser = new VoiceFeaturesParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def serialize(topic: String, voiceFeatures: VoiceFeatures): Array[Byte] = {
    return voiceFeaturesParser.parse(voiceFeatures).getBytes
  }

  override def close(): Unit = {

  }
}

class VoiceFeaturesDeserializer extends Deserializer[VoiceFeatures] {
  val voiceFeaturesParser = new VoiceFeaturesParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def deserialize(topic: String, data: Array[Byte]): VoiceFeatures = {
    return voiceFeaturesParser.parse(new String(data))
  }

  override def close(): Unit = {

  }
}