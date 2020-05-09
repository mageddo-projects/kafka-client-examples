package kafka.client.vanilla;

import com.mageddo.kafka.client.DefaultCallbackContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import testing.TestKafkaConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

class StockBuyOrderConsumerTest {

  @Test
  void mustValidateStock() {
    // arrange
    final var consumer = new StockBuyOrderConsumer(TestKafkaConfig.consumers());

    // act
    final var record = new ConsumerRecord<>("topic", 0, 0, "key", "symbol=AXB");
    final var callbackContext = DefaultCallbackContext
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
