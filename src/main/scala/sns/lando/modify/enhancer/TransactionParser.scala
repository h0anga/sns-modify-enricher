package sns.lando.modify.enhancer

import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import org.json4s.{Formats, ShortTypeHints}

class TransactionParser {

  def parse(textLine: String): Transaction = {
    implicit val formats: Formats = Serialization.formats (ShortTypeHints(List(classOf[String])))
    println(s"Incoming transaction to parse: ${textLine}")
    read[Transaction](textLine)
  }

  def parse(transaction: Transaction): String = {
    implicit val formats: Formats = Serialization.formats (ShortTypeHints(List(classOf[String])))
    println("Unparsing a Transaction")
    write(transaction)
  }
}
