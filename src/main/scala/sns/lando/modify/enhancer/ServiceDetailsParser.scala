package sns.lando.modify.enhancer

import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, NoTypeHints}

class ServiceDetailsParser {
  def parse(textLine: String): ServiceDetails = {
    implicit val formats: Formats = Serialization.formats(NoTypeHints)
    println(s"Incoming ServiceDetails to parse: ${textLine}")
    read[ServiceDetails](textLine)
  }

  def parse(serviceDetails: ServiceDetails): String = {
    implicit val formats: Formats = Serialization.formats(NoTypeHints)
    println("Unparsing a ServiceDetails")
    write(serviceDetails)
  }
}
