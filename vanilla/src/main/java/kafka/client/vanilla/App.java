package kafka.client.vanilla;

import org.apache.kafka.clients.producer.ProducerRecord;

public class App {
  public static void main(String[] args) throws InterruptedException {

    final var consumers = KafkaConfig.consumers();
    new StockConsumer(consumers);
    new StockBuyOrderConsumer(consumers);

    keepProducingMsgs();

  }

  static void keepProducingMsgs() throws InterruptedException {
    final var producer = KafkaConfig.producer();
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
