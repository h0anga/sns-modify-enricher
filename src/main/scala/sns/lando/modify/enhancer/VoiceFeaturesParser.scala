package sns.lando.modify.enhancer

import org.json4s.ParserUtil.ParseException
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, NoTypeHints}

class VoiceFeaturesParser {

  def parse(textLine: String): VoiceFeatures = {
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    println(s"Incoming message to parse: ${textLine}")
    read[VoiceFeatures](textLine)

//    val voiceFeatures: VoiceFeatures = try {
//      read[VoiceFeatures](textLine)
//    } catch {
//      case e: ParseException => return Option.empty
//    }

//    write(voiceFeatures)
//    Option.apply(voiceFeatures)
  }

  def parse(voiceFeatures: VoiceFeatures): String = {
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    println("Unparsing a Voicefeatures")
    write(voiceFeatures)
  }
}
