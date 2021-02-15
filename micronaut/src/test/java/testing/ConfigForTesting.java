package testing;

import com.mageddo.kafka.client.ConsumerConfig;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;

@Factory
public class ConfigForTesting {

  @Primary
  @Context
  public Producer<String, String> producer() {
    return new MockProducer<>();
  }

  @Primary
  @Context
  public ConsumerConfig<String, String> consumers() {
    return ConsumerConfig
      .<String, String>builder()
      .consumers(Integer.MIN_VALUE) // disabling consumers
      .build()
      ;
  }
}
