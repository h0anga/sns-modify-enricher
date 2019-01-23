package sns.lando.modify.enhancer

import org.json4s.native.Serialization
import org.json4s.native.Serialization.read
import org.json4s.{Formats, NoTypeHints}

class KnitwareConverter {

  def getXmlFor(textLine: String): String = {
    println(s"input: ${textLine}")
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    val voiceFeatures = read[VoiceFeatures] (textLine)
    println(s"vf: ${voiceFeatures.netstreamCorrelationId}")

    return s"""
              |{"netstreamCorrelationId":"${voiceFeatures.netstreamCorrelationId}"}
    """.stripMargin
  }
}
