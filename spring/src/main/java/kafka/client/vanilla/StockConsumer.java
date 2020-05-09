package kafka.client.vanilla;

import com.mageddo.kafka.client.ConsumeCallback;
import com.mageddo.kafka.client.Consumers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Component
public class StockConsumer implements InitializingBean {

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

  @Override
  public void afterPropertiesSet() {
    this.consumers
      .toBuilder()
      .consumers(3)
      .prop(GROUP_ID_CONFIG, "spring_stock")
      .topics("stock_changed")
      .callback(this.consume())
      .build()
      .consume();
  }
}
