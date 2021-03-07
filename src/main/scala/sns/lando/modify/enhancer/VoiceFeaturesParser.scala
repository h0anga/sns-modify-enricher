package sns.lando.modify.enhancer

import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, ShortTypeHints}

class VoiceFeaturesParser {

  def parse(textLine: String): InValue = {
    implicit val formats: Formats = Serialization.formats (ShortTypeHints(List(classOf[String])))
    println(s"Incoming unenhanced instruction to parse: ${textLine}")
    read[InValue](textLine)
  }

  def parse(voiceFeatures: InValue): String = {
    implicit val formats: Formats = Serialization.formats (ShortTypeHints(List(classOf[String])))
    println("Unparsing a Transaction")
    write(voiceFeatures)
  }
}
