package sns.lando.modify.enhancer.serdes

import java.util

import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.VoiceFeatures

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
