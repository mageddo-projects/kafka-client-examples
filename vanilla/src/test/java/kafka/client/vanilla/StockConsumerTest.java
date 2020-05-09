package kafka.client.vanilla;

import com.mageddo.kafka.client.DefaultCallbackContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import testing.TestKafkaConfig;

class StockConsumerTest {
  @Test
  void mustUpdatePrices() throws Exception {
    // arrange
    final var consumer = new StockConsumer(TestKafkaConfig.consumers());

    // act
    final var record = new ConsumerRecord<>("topic", 0, 0, "key", "some message");
    final var callbackContext = DefaultCallbackContext
      .<String, String>NOP()
      .toBuilder()
      .record(record)
      .build()
      ;

    consumer
      .consume()
      .accept(callbackContext, record)
    ;

    // assert
  }
}
