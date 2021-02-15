package kafka.client.micronaut;

import com.mageddo.kafka.client.ConsumeCallback;
import com.mageddo.kafka.client.Consumer;
import com.mageddo.kafka.client.ConsumerConfig;
import com.mageddo.kafka.client.RecoverCallback;
import com.mageddo.kafka.client.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.time.Duration;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Singleton
public class StockBuyOrderConsumer implements Consumer {

  private final Logger log = LoggerFactory.getLogger(getClass());

  ConsumeCallback<String, String> consume() {
    return (ctx, record) -> {
      if (record.value().contains("symbol=A")) {
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

  @Override
  public ConsumerConfig<String, String> config() {
    return ConsumerConfig
      .<String, String>builder()
      .retryPolicy(RetryPolicy
        .builder()
        .delay(Duration.ofSeconds(5))
        .maxTries(0)
        .build()
      )
      .consumers(3)
      .prop(GROUP_ID_CONFIG, "micronaut_stock_buy")
      .topics("stock_buy_order")
      .callback(this.consume())
      .recoverCallback(this.recover())
      .build();
  }
}
