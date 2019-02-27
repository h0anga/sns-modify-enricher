package sns.lando.modify.enhancer

import org.json4s.ParserUtil.ParseException
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, NoTypeHints}

class ModifyVoiceFeaturesInstructionSerializer {
  def serialize(instruction: ModifyVoiceFeaturesInstruction) : String = {
    println("Called the MVFI Serializer")
    implicit val formats: Formats = Serialization.formats (NoTypeHints)

    write(instruction)
  }
}
