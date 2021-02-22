package rht.kafka.config

import com.typesafe.config.Config
import org.apache.kafka.clients.consumer.ConsumerConfig

final case class KafkaConsumerConfig(topic: String, props: Map[String, String])
object KafkaConsumerConfig {
  def apply(config: Config): KafkaConsumerConfig = {
    val topic = config.getString("topic")
    KafkaConsumerConfig(topic, KafkaConfigReader(config))
  }
}

final case class KafkaProducerConfig(topic: String, props: Map[String, String])
object KafkaProducerConfig {
  def apply(config: Config): KafkaProducerConfig = {
    val topic = config.getString("topic")
    KafkaProducerConfig(topic, KafkaConfigReader(config))
  }
}

object KafkaConfigReader {
  def apply(config: Config): Map[String, String] = {
    import collection.JavaConverters._
    ConsumerConfig
      .configNames()
      .asScala
      .toSet[String]
      .map { key =>
        if (config.hasPath(key)) {
          Some(key -> config.getString(key))
        } else {
          None
        }
      }
      .collect { case Some(prop) => prop }
      .toMap
  }

}
