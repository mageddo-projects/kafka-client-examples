package kafka.client.vanilla;

import com.mageddo.kafka.client.CallbackContext;
import com.mageddo.kafka.client.DefaultCallbackContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import testing.TestKafkaConfig;

class StockConsumerTest {
  @Test
  void mustUpdatePrices() throws Exception {
    // arrange
    final StockConsumer consumer = new StockConsumer(TestKafkaConfig.consumers());

    // act
    final ConsumerRecord<String, String> record = new ConsumerRecord<>(
      "topic", 0, 0, "key", "some message"
    );
    final CallbackContext<String, String> callbackContext = DefaultCallbackContext
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
