package rht.hack.components

import com.typesafe.config.ConfigFactory
import rht.kafka.config.KafkaConsumerConfig

case class ConfigComponent(kafkaConsumer: KafkaConsumerConfig)

object ConfigComponent {
  private lazy val config = ConfigFactory.load()

  def apply(): ConfigComponent = {
    val consumerConfig = KafkaConsumerConfig(config.getConfig("app.kafka.candles.consumer"))

    ConfigComponent(consumerConfig)
  }

}
