package sns.lando.modify.enhancer.serdes

import java.util

import org.apache.kafka.common.serialization.Serializer
import sns.lando.modify.enhancer.{VoiceFeatures, VoiceFeaturesParser}

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
