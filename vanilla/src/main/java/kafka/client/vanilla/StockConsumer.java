package kafka.client.vanilla;

import com.mageddo.kafka.client.ConsumeCallback;
import com.mageddo.kafka.client.Consumers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

public class StockConsumer {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final Consumers<String, String> consumers;

  public StockConsumer(Consumers<String, String> consumers) {
    this.consumers = consumers;
    this.init();
  }

  public void init() {
    this.consumers
      .toBuilder()
      .consumers(3)
      .prop(GROUP_ID_CONFIG, "vanilla_stock")
      .topics("stock_changed")
      .callback(this.consume())
      .build()
      .consume();
  }

  ConsumeCallback<String, String> consume() {
    return (ctx, record) -> {
      log.info("status=consumed, {}", record.value());
    };
  }
}
