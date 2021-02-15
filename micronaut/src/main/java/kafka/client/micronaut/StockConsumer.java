package kafka.client.micronaut;

import com.mageddo.kafka.client.ConsumeCallback;
import com.mageddo.kafka.client.Consumer;
import com.mageddo.kafka.client.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Singleton
public class StockConsumer implements Consumer {

  private final Logger log = LoggerFactory.getLogger(getClass());

  ConsumeCallback<String, String> consume() {
    return (ctx, record) -> {
      log.info("status=price-updated, {}", record.value());
    };
  }

  @Override
  public ConsumerConfig<String, String> config() {
    return ConsumerConfig
      .<String, String>builder()
      .consumers(3)
      .prop(GROUP_ID_CONFIG, "micronaut_stock")
      .topics("stock_changed")
      .callback(this.consume())
      .build();
  }
}
