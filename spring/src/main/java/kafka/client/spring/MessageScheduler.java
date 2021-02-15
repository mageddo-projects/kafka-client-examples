package kafka.client.spring;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageScheduler {

  public static final int ONE_SECOND_IN_MILLIS = 1000;
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final Producer<String, String> producer;

  public MessageScheduler(Producer<String, String> producer) {
    this.producer = producer;
  }

  @Scheduled(fixedDelay = ONE_SECOND_IN_MILLIS)
  public void keepProducingMsgs() {
    log.info("status=send-stock-messages");
    this.producer.send(new ProducerRecord<>(
      "stock_changed",
      String.format("symbol=%s, amount=%.2f", randomSymbol(), Math.random())
    ));

    this.producer.send(new ProducerRecord<>(
      "stock_buy_order",
      String.format("symbol=%s, amount=%.2f, expires_in=2 minutes", randomSymbol(), Math.random())
    ));
  }

  static String randomSymbol() {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      sb.append((char) ((int) (Math.random() * 100) % 26 + 65));
    }
    return sb.toString();
  }
}
