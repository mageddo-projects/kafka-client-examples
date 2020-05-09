package kafka.client.vanilla;

import com.mageddo.kafka.client.CallbackContext;
import com.mageddo.kafka.client.DefaultCallbackContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import testing.TestKafkaConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

class StockBuyOrderConsumerTest {

  @Test
  void mustValidateStock() {
    // arrange
    final StockBuyOrderConsumer consumer = new StockBuyOrderConsumer(TestKafkaConfig.consumers());

    // act
    final ConsumerRecord<String, String> record = new ConsumerRecord<>(
      "topic", 0, 0, "key", "symbol=AXB"
    );
    final CallbackContext<String, String> callbackContext = DefaultCallbackContext
      .<String, String>NOP()
      .toBuilder()
      .record(record)
      .build()
      ;

    assertThrows(IllegalArgumentException.class, () -> {
      consumer
        .consume()
        .accept(callbackContext, record);
    })
    ;

    // assert
  }

}
