package sns.lando.modify.enhancer

import org.json4s.ParserUtil.ParseException
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, NoTypeHints}

class EnrichedInstructionSerializer {
  def serialize(instruction: EnrichedInstruction) : String = {
    println("Called the MVFI Serializer")
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    s"""{"enrichedInstruction":${write(instruction)}}"""
  }
}
