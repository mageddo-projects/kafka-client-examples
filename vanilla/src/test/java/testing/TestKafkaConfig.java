package testing;

import com.mageddo.kafka.client.Consumers;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;

public class TestKafkaConfig {

  public static Producer<String, String> producer() {
    return new MockProducer<>();
  }

  public static Consumers<String, String> consumers() {
    return Consumers
      .<String, String>builder()
      .consumers(Integer.MIN_VALUE) // disabling consumers
      .build()
      ;
  }
}
