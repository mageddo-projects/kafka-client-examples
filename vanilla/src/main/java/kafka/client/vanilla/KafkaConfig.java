package kafka.client.vanilla;

import com.mageddo.kafka.client.ConsumerConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class KafkaConfig {

  public static Producer<String, String> producer() {
    final Map<String, Object> props = new LinkedHashMap<>();
    props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return new KafkaProducer<>(props);
  }

  public static ConsumerConfig<String, String> defaultConfig() {
    return ConsumerConfig
      .<String, String>builder()
      .prop(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      .prop(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
      .prop(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
      .build()
      ;
  }
}
