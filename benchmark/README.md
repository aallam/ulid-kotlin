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
ULIDAverage.benchmark_nextULID_toString      avgt    5      676.207 ±     5.193    ns/op
ULIDAverage.benchmark_randomULID             avgt    5      644.570 ±     3.677    ns/op
ULIDAverage.benchmark_randomUUID             avgt    5      449.208 ±     4.799    ns/op
ULIDThroughput.benchmark_nextULID_toString  thrpt    5  1494217.244 ± 26882.297  ops/sec
ULIDThroughput.benchmark_randomULID         thrpt    5  1521867.091 ± 18060.507  ops/sec
ULIDThroughput.benchmark_randomUUID         thrpt    5  2218858.340 ± 43712.400  ops/sec
````
