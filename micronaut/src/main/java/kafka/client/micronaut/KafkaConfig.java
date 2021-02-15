package kafka.client.micronaut;

import com.mageddo.kafka.client.Consumer;
import com.mageddo.kafka.client.ConsumerConfig;
import com.mageddo.kafka.client.ConsumerStarter;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

@Factory
public class KafkaConfig {

  @Singleton
  public Producer<String, String> producer() {
    final Map<String, Object> props = new LinkedHashMap<>();
    props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return new KafkaProducer<>(props);
  }

  @Singleton
  public ConsumerConfig<String, String> defaultConsumerConfig() {
    return ConsumerConfig
      .<String, String>builder()
      .prop(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      .prop(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
      .prop(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
      .build()
      ;
  }

  @Context
  static class ConsumerConfigurer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final List<Consumer> consumers;
    private final ConsumerStarter consumerStarter;

    @Inject
    public ConsumerConfigurer(List<Consumer> consumers, ConsumerConfig<String, String> consumerConfig) {
      this.consumers = consumers;
      this.consumerStarter = new ConsumerStarter(consumerConfig);
    }

    @PostConstruct
    public void construct(){
      this.consumerStarter.start(this.consumers);
    }

    @PreDestroy
    public void destroy(){
      log.info("status=consumers-stopping");
      this.consumerStarter.stop();
      log.info("status=consumers-stopped");
    }
  }
}
