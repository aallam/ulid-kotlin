# Benchmarks

```sh
./gradlew benchmark
```

On 2,2 GHz Intel Core i7, macOS 13.1 and Java 17.0.6 (Temurin):

### JVM
````
Benchmark                                    Mode  Cnt         Score        Error  Units
ULIDAverage.benchmark_nextULID_toString      avgt    5        77.436 ±      2.000  ns/op
ULIDAverage.benchmark_randomULID             avgt    5        78.005 ±      4.683  ns/op
ULIDAverage.benchmark_randomUUID             avgt    5       355.228 ±     32.852  ns/op
ULIDThroughput.benchmark_nextULID_toString  thrpt    5  13126798.435 ±  87624.877  ops/s
ULIDThroughput.benchmark_randomULID         thrpt    5  12996685.137 ± 505180.013  ops/s
ULIDThroughput.benchmark_randomUUID         thrpt    5   2849169.607 ±  63576.937  ops/s
````

### JS
````
Benchmark                                    Mode  Cnt         Score        Error    Units
ULIDAverage.benchmark_nextULID_toString      avgt    5      3413.039 ±    134.168    ns/op
ULIDAverage.benchmark_randomULID             avgt    5      3153.881 ±      9.269    ns/op
ULIDAverage.benchmark_randomUUID             avgt    5        55.647 ±      0.501    ns/op
ULIDThroughput.benchmark_nextULID_toString  thrpt    5    295730.038 ±  24753.945  ops/sec
ULIDThroughput.benchmark_randomULID         thrpt    5    319544.621 ±   2000.374  ops/sec
ULIDThroughput.benchmark_randomUUID         thrpt    5  17505839.286 ± 346412.077  ops/sec
````

### Native (MacOS)
````
Benchmark                                    Mode  Cnt        Score       Error    Units
ULIDAverage.benchmark_nextULID_toString      avgt    5      712.336 ±     62.898    ns/op
ULIDAverage.benchmark_randomULID             avgt    5      610.932 ±     43.855    ns/op
ULIDAverage.benchmark_randomUUID             avgt    5     1165.665 ±    200.091    ns/op
ULIDThroughput.benchmark_nextULID_toString  thrpt    5  1652419.335 ±  46228.572  ops/sec
ULIDThroughput.benchmark_randomULID         thrpt    5  1715782.581 ± 138568.223  ops/sec
ULIDThroughput.benchmark_randomUUID         thrpt    5   884488.773 ±  95637.845  ops/sec
````
