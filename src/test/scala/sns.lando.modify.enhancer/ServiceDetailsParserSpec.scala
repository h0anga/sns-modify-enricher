package sns.lando.modify.enhancer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.UUID

class ServiceDetailsParserSpec extends AnyFlatSpec with Matchers {
  private val parser = new ServiceDetailsParser

  private val serviceId = UUID.randomUUID().toString
  private val serviceDetailsJson = s"""{"serviceId":"$serviceId","serviceSpecCode":"VoipService","directoryNumber":"01202000095"}"""
  private val serviceDetails = ServiceDetails(s"$serviceId","VoipService","01202000095")

  it should "test serviceDetails parsing" in {
    org.json4s.native.JsonMethods
    parser.parse(serviceDetails) shouldEqual serviceDetailsJson
  }

  it should "test serviceDetailsJson parsing" in {
    parser.parse(serviceDetailsJson) shouldEqual serviceDetails
  }
}
