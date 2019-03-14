package sns.lando.modify.enhancer

import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, NoTypeHints}

class VoiceFeaturesParser {

  def parse(textLine: String): ModifyVoiceFeaturesMessage = {
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    println(s"Incoming unenhanced instruction to parse: ${textLine}")
    read[ModifyVoiceFeaturesMessage](textLine)
  }

  def parse(voiceFeatures: ModifyVoiceFeaturesMessage): String = {
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    println("Unparsing a ModifyVoiceFeaturesMessage")
    write(voiceFeatures)
  }
}
