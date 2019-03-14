package sns.lando.modify.enhancer

import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, NoTypeHints}

class EnrichedInstructionParser {

  def parse(textLine: String): EnrichedInstruction = {
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    println(s"Incoming EnrichedInstruction to parse: ${textLine}")
    read[EnrichedInstruction](textLine)
  }

  def parse(enrichedInstruction: EnrichedInstruction): String = {
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    println("Unparsing a EnrichedInstruction")
    write(enrichedInstruction)
  }
}
