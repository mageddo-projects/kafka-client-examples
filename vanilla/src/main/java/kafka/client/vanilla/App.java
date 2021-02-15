package kafka.client.vanilla;

import com.mageddo.kafka.client.ConsumerStarter;
import com.sun.tools.javac.util.List;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class App {
  public static void main(String[] args) throws InterruptedException {

    final ConsumerStarter consumerStarter = ConsumerStarter.start(KafkaConfig.defaultConfig(), List.of(
      new StockConsumer(), new StockBuyOrderConsumer()
    ));
    try {
      keepProducingMsgs();
    } finally {
      consumerStarter.stop();
    }
  }

  static void keepProducingMsgs() throws InterruptedException {
    final Producer<String, String> producer = KafkaConfig.producer();
    for (;;){
      producer.send(new ProducerRecord<>(
        "stock_changed",
        String.format("symbol=%s, amount=%.2f", randomSymbol(), Math.random())
      ));

      producer.send(new ProducerRecord<>(
        "stock_buy_order",
        String.format("symbol=%s, amount=%.2f, expires_in=2 minutes", randomSymbol(), Math.random())
      ));

      Thread.sleep(1000);
    }
  }

  static String randomSymbol() {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i <3; i++) {
      sb.append((char) ((int) (Math.random() * 100) % 26 + 65));
    }
    return sb.toString();
  }
}
