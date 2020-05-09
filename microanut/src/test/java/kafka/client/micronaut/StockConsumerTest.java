package kafka.client.micronaut;

import com.mageddo.kafka.client.CallbackContext;
import com.mageddo.kafka.client.DefaultCallbackContext;
import io.micronaut.test.annotation.MicronautTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
class StockConsumerTest {

  @Inject
  private StockConsumer consumer;

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
