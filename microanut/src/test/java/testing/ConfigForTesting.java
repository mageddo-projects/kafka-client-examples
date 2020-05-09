package testing;

import com.mageddo.kafka.client.Consumers;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;

@Factory
public class ConfigForTesting {

  @Bean
  @Replaces(Producer.class)
  public Producer<String, String> producer() {
    return new MockProducer<>();
  }

  @Bean
  @Replaces(Consumers.class)
  public static Consumers<String, String> consumers() {
    return Consumers
      .<String, String>builder()
      .consumers(Integer.MIN_VALUE) // disabling consumers
      .build()
      ;
  }
}
