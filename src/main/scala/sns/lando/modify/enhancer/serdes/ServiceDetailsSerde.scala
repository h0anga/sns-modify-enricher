package sns.lando.modify.enhancer.serdes

import java.util

import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.{ServiceDetails, ServiceDetailsParser}

class ServiceDetailsSerde extends Serde[ServiceDetails] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def close(): Unit = {

  }

  override def serializer(): Serializer[ServiceDetails] = {
    return new ServiceDetailsSerializer()
  }

  override def deserializer(): Deserializer[ServiceDetails] = {
    return new ServiceDetailsDeserializer()
  }
}

class ServiceDetailsSerializer() extends Serializer[ServiceDetails] {
  val serviceDetailsParser = new ServiceDetailsParser()

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
  }

  override def serialize(topic: String, data: ServiceDetails): Array[Byte] = {
    serviceDetailsParser.parse(data).getBytes()
  }

  override def close(): Unit = {
  }
}

class ServiceDetailsDeserializer() extends Deserializer[ServiceDetails] {
  val serviceDetailsParser = new ServiceDetailsParser()

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {

  }

  override def deserialize(topic: String, data: Array[Byte]): ServiceDetails = {
    serviceDetailsParser.parse(new String(data))
  }

  override def close(): Unit = {

  }
}

