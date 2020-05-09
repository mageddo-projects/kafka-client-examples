package kafka.client.vanilla;

import com.mageddo.kafka.client.CallbackContext;
import com.mageddo.kafka.client.DefaultCallbackContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.ConfigForTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ConfigForTesting.class)
@ExtendWith(SpringExtension.class)
class StockBuyOrderConsumerTest {

  @Autowired
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
      .build()
      ;

    assertThrows(IllegalArgumentException.class, () -> {
      this.consumer
        .consume()
        .accept(callbackContext, record);
    })
    ;

    // assert
  }

}
