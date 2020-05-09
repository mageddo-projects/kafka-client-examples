package kafka.client.micronaut;

import com.mageddo.kafka.client.CallbackContext;
import com.mageddo.kafka.client.DefaultCallbackContext;
import io.micronaut.test.annotation.MicronautTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class StockBuyOrderConsumerTest {

  @Inject
  private StockBuyOrderConsumer consumer;

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
