package testing;

import com.mageddo.kafka.client.Consumers;
import kafka.client.spring.App;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(App.class)
public class ConfigForTesting {

  @Bean
  @Primary
  public Producer<String, String> producer() {
    return new MockProducer<>();
  }

  @Bean
  @Primary
  public static Consumers<String, String> consumers() {
    return Consumers
      .<String, String>builder()
      .consumers(Integer.MIN_VALUE) // disabling consumers
      .build()
      ;
  }
}
