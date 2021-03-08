package sns.lando.modify.enhancer.serdes

import java.util
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}
import sns.lando.modify.enhancer.{Transaction, TransactionParser}

class TransactionSerde extends Serde[Transaction] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def close(): Unit = {}

  override def serializer(): Serializer[Transaction] = new TransactionSerializer()

  override def deserializer(): Deserializer[Transaction] = new TransactionDeserializer()
}

class TransactionSerializer extends Serializer[Transaction] {
  val transactionParser = new TransactionParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def serialize(topic: String, transaction: Transaction): Array[Byte] = transactionParser.parse(transaction).getBytes

  override def close(): Unit = {}
}

class TransactionDeserializer extends Deserializer[Transaction] {
  val transactionParser = new TransactionParser()
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def deserialize(topic: String, data: Array[Byte]): Transaction = transactionParser.parse(new String(data))

  override def close(): Unit = {}
}