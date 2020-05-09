package kafka.client.quarkus;

import com.mageddo.kafka.client.ConsumeCallback;
import com.mageddo.kafka.client.Consumers;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Singleton
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

  public void init(@Observes StartupEvent event) {
    this.consumers
      .toBuilder()
      .consumers(3)
      .prop(GROUP_ID_CONFIG, "quarkus_stock")
      .topics("stock_changed")
      .callback(this.consume())
      .build()
      .consume();
  }
}
