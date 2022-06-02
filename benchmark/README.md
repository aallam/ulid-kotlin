# Benchmarks

```sh
./gradlew benchmark
```

On 2,2 GHz Intel Core i7, macOS 12.4 and Java 17.0.3 (Temurin):

### JVM
````
Benchmark                                    Mode  Cnt         Score         Error  Units
ULIDAverage.benchmark_nextULID_toString      avgt    5        73.037 ±       0.408  ns/op
ULIDAverage.benchmark_randomULID             avgt    5        73.371 ±       0.204  ns/op
ULIDAverage.benchmark_randomUUID             avgt    5       356.412 ±      21.441  ns/op
ULIDThroughput.benchmark_nextULID_toString  thrpt    5  13185368.442 ± 2923436.139  ops/s
ULIDThroughput.benchmark_randomULID         thrpt    5  13639833.380 ±  329058.644  ops/s
ULIDThroughput.benchmark_randomUUID         thrpt    5   2861013.705 ±   49243.224  ops/s
````

### JS
````
Benchmark                                    Mode  Cnt         Score         Error    Units
ULIDAverage.benchmark_nextULID_toString      avgt    5       864.345 ±      5.740    ns/op
ULIDAverage.benchmark_randomULID             avgt    5       805.020 ±      5.165    ns/op
ULIDAverage.benchmark_randomUUID             avgt    5        55.493 ±      0.798    ns/op
ULIDThroughput.benchmark_nextULID_toString  thrpt    5   1166417.593 ±   4791.949  ops/sec
ULIDThroughput.benchmark_randomULID         thrpt    5   1236587.846 ±  12895.702  ops/sec
ULIDThroughput.benchmark_randomUUID         thrpt    5  17962651.946 ± 485702.176  ops/sec
````

### Native (MacOS)
````
Benchmark                                    Mode  Cnt        Score       Error    Units
ULIDAverage.benchmark_nextULID_toString      avgt    5      700.184 ±      6.235    ns/op
ULIDAverage.benchmark_randomULID             avgt    5      674.403 ±     13.308    ns/op
ULIDAverage.benchmark_randomUUID             avgt    5     1077.795 ±    160.782    ns/op
ULIDThroughput.benchmark_nextULID_toString  thrpt    5  1257132.750 ± 142648.484  ops/sec
ULIDThroughput.benchmark_randomULID         thrpt    5  1543210.766 ±  21209.563  ops/sec
ULIDThroughput.benchmark_randomUUID         thrpt    5  1156071.515 ± 129366.446  ops/sec

````
