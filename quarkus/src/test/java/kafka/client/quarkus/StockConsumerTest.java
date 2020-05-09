package kafka.client.quarkus;

import com.mageddo.kafka.client.CallbackContext;
import com.mageddo.kafka.client.DefaultCallbackContext;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
class StockConsumerTest {

  @Inject
  StockConsumer consumer;

  @Test
  void mustUpdatePrices() throws Exception {
    // arrange

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

    this.consumer
      .consume()
      .accept(callbackContext, record)
    ;

    // assert
  }
}
