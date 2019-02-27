package sns.lando.modify.enhancer.serdes

import java.util

import org.apache.kafka.common.serialization.Deserializer
import sns.lando.modify.enhancer.{VoiceFeatures, VoiceFeaturesParser}

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
