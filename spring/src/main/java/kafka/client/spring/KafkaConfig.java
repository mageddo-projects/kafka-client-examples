package kafka.client.spring;

import com.mageddo.kafka.client.Consumer;
import com.mageddo.kafka.client.ConsumerConfig;
import com.mageddo.kafka.client.ConsumerStarter;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

@Configuration
public class KafkaConfig implements InitializingBean {

  private final List<Consumer> consumers;
  private final ConsumerStarter consumerStarter;

  @Autowired
  public KafkaConfig(List<Consumer> consumers, ConsumerConfig<String, String> consumerConfig) {
    this.consumers = consumers;
    this.consumerStarter = new ConsumerStarter(consumerConfig);
  }

  @Override
  public void afterPropertiesSet() {
    this.consumerStarter.start(this.consumers);
  }

  @PreDestroy
  public void onShutdown() {
    this.consumerStarter.stop();
  }

  @Configuration
  public static class ProducerConfig {

    @Bean
    public ConsumerConfig<String, String> defaultConsumerConfig() {
      return ConsumerConfig
        .<String, String>builder()
        .prop(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        .prop(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        .prop(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        .build()
        ;
    }

    @Bean
    public Producer<String, String> producer() {
      final Map<String, Object> props = new LinkedHashMap<>();
      props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      return new KafkaProducer<>(props);
    }
  }
}
