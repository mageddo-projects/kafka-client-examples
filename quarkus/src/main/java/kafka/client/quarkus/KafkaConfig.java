package kafka.client.quarkus;

import com.mageddo.kafka.client.Consumer;
import com.mageddo.kafka.client.ConsumerConfig;
import com.mageddo.kafka.client.ConsumerStarter;
import io.quarkus.arc.DefaultBean;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

@Singleton
public class KafkaConfig {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final ConsumerStarter consumerStarter;

  @Inject
  public KafkaConfig(ConsumerConfig<String, String> consumerConfig) {
    this.consumerStarter = new ConsumerStarter(consumerConfig);
  }

  public void onStartup(@Observes StartupEvent startupEvent, Instance<Consumer> consumers) {
    this.consumerStarter.start(consumers
      .stream()
      .collect(Collectors.toList())
    );
  }

  public void onShutdown(@Observes ShutdownEvent shutdownEvent) {
    this.consumerStarter.stop();
    log.info("status=consumers-were-shutdown");
  }

  public static class ProducerAndConsumerConfig {
    @Produces
    @DefaultBean
    public Producer<String, String> producer() {
      final Map<String, Object> props = new LinkedHashMap<>();
      props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      return new KafkaProducer<>(props);
    }

    @Produces
    @DefaultBean
    public ConsumerConfig<String, String> consumers() {
      return ConsumerConfig
        .<String, String>builder()
        .prop(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        .prop(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        .prop(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        .build()
        ;
    }
  }
}
