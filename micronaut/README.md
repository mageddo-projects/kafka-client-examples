```
$ ./gradlew run
[pool-3-thread-1] kafka.client.micronaut.StockConsumer       : status=price-updated, symbol=CNH, amount=0,35
[pool-1-thread-1] k.client.vanilla.StockBuyOrderConsumer   : status=recover, msg=Can't buy symbols starting with letter 'A'! (Don't ask me why), stock=symbol=AQC, amount=0,12, expires_in=2 minutes
[pool-1-thread-1] k.client.vanilla.StockBuyOrderConsumer   : status=bought, symbol=CMY, amount=1,00, expires_in=2 minutes
```
