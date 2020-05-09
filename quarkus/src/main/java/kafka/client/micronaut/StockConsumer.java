package kafka.client.micronaut;

import com.mageddo.kafka.client.ConsumeCallback;
import com.mageddo.kafka.client.Consumers;
import io.micronaut.context.annotation.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Context
public class StockConsumer {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final Consumers<String, String> consumers;

  public StockConsumer(Consumers<String, String> consumers) {
    this.consumers = consumers;
  }

  ConsumeCallback<String, String> consume() {
    return (ctx, record) -> {
      log.info("status=price-updated, {}", record.value());
    };
  }

  @PostConstruct
  public void init() {
    this.consumers
      .toBuilder()
      .consumers(3)
      .prop(GROUP_ID_CONFIG, "micronaut_stock")
      .topics("stock_changed")
      .callback(this.consume())
      .build()
      .consume();
  }
}
