## Standard jar
```bash
$ ./gradlew quarkusDev

[pool-3-thread-1] kafka.client.quarkus.StockConsumer       : status=price-updated, symbol=CNH, amount=0,35
[pool-1-thread-1] k.client.vanilla.StockBuyOrderConsumer   : status=recover, msg=Can't buy symbols starting with letter 'A'! (Don't ask me why), stock=symbol=AQC, amount=0,12, expires_in=2 minutes
[pool-1-thread-1] k.client.vanilla.StockBuyOrderConsumer   : status=bought, symbol=CMY, amount=1,00, expires_in=2 minutes
```

## Native Image
```bash
$ ./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true

[kaf.cli.qua.StockConsumer] (pool-3-thread-1) status=price-updated, symbol=QET, amount=0,82
[kaf.cli.qua.StockBuyOrderConsumer] (pool-5-thread-1) status=recover, msg=Can't buy symbols starting with letter 'A'! (Don't ask me why), stock=symbol=ARZ, amount=0,04, expires_in=2 minutes
[kaf.cli.qua.StockBuyOrderConsumer] (pool-5-thread-1) status=bought, symbol=RYR, amount=0,93, expires_in=2 minutes
```
