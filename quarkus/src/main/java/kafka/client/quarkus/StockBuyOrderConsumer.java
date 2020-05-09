package kafka.client.quarkus;

import com.mageddo.kafka.client.ConsumeCallback;
import com.mageddo.kafka.client.Consumers;
import com.mageddo.kafka.client.RecoverCallback;
import com.mageddo.kafka.client.RetryPolicy;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import java.time.Duration;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Singleton
public class StockBuyOrderConsumer {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final Consumers<String, String> consumers;

  public StockBuyOrderConsumer(Consumers<String, String> consumers) {
    this.consumers = consumers;
  }

  ConsumeCallback<String, String> consume() {
    return (ctx, record) -> {
      if(record.value().contains("symbol=A")){
        throw new IllegalArgumentException(
          "Can't buy symbols starting with letter 'A'! (Don't ask me why), stock=" + record.value()
        );
      }
      log.info("status=bought, {}", record.value());
    };
  }

  RecoverCallback<String, String> recover() {
    return ctx -> log.info("status=recover, msg={}", ctx.lastFailure().getMessage());
  }

  public void init(@Observes StartupEvent event) {
    this.consumers
      .toBuilder()
      .retryPolicy(RetryPolicy
        .builder()
        .delay(Duration.ofSeconds(5))
        .maxTries(0)
        .build()
      )
      .consumers(3)
      .prop(GROUP_ID_CONFIG, "quarkus_stock_buy")
      .topics("stock_buy_order")
      .callback(this.consume())
      .recoverCallback(this.recover())
      .build()
      .consume();
  }
}
