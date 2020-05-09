package kafka.client.quarkus;

import com.mageddo.kafka.client.CallbackContext;
import com.mageddo.kafka.client.DefaultCallbackContext;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class StockBuyOrderConsumerTest {

  @Inject
  StockBuyOrderConsumer consumer;

  @Test
  void mustValidateStock() {
    // arrange

    // act
    final ConsumerRecord<String, String> record = new ConsumerRecord<>(
      "topic", 0, 0, "key", "symbol=AXB"
    );
    final CallbackContext<String, String> callbackContext = DefaultCallbackContext
      .<String, String>NOP()
      .toBuilder()
      .record(record)
      .build();

    assertThrows(IllegalArgumentException.class, () -> {
      this.consumer
        .consume()
        .accept(callbackContext, record);
    })
    ;

    // assert
  }

}
