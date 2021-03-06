package testing;

import com.mageddo.kafka.client.ConsumerConfig;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;

import javax.enterprise.inject.Produces;

public class ConfigForTesting {

  @Produces
  public Producer<String, String> producer() {
    return new MockProducer<>();
  }

  @Produces
  public ConsumerConfig<String, String> consumers() {
    return ConsumerConfig
      .<String, String>builder()
      .consumers(Integer.MIN_VALUE) // disabling consumers
      .build()
      ;
  }
}
