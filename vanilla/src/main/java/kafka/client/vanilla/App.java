package kafka.client.vanilla;

import org.apache.kafka.clients.producer.ProducerRecord;

public class App {

  public String getGreeting() {
    return "Hello world.";
  }

  public static void main(String[] args) throws InterruptedException {

    new StockConsumer(KafkaConfig.consumers());

    final var producer = KafkaConfig.producer();
    for (;;){
      Thread.sleep(1000);
      producer.send(new ProducerRecord<>(
        "stock_changed",
        String.format("symbol=%s, amount=%.2f", randomSymbol(), Math.random())
      ));
    }
  }

  private static String randomSymbol() {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      sb.append((char) ((int) (Math.random() * 100) % 26 + 65));
    }
    return sb.toString();
  }
}
