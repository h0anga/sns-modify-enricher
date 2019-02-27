package sns.lando.modify.enhancer

import org.json4s.ParserUtil.ParseException
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, NoTypeHints}

class ServiceDetailsParser {
  def parse(textLine: String): ServiceDetails = {
    implicit val formats: Formats = Serialization.formats(NoTypeHints)
    println(s"Incoming ServiceDetails to parse: ${textLine}")
    read[ServiceDetails](textLine)
//    val serviceDetails: ServiceDetails = try {
//      read[ServiceDetails](textLine)
//    } catch {
//      case e: ParseException => return Option.empty
//    }
//    Option.apply(serviceDetails)
  }
}
