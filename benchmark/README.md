# Benchmarks

```sh
./gradlew benchmark
```

On 2,2 GHz Intel Core i7, macOS 12.4 and Java 17.0.3 (Temurin):

### JVM
````
Benchmark                                    Mode  Cnt         Score         Error  Units
ULIDThroughput.benchmark_nextULID_toString  thrpt    5  12346240.309 ± 4877126.024  ops/s
ULIDThroughput.benchmark_randomULID         thrpt    5  13316084.195 ±  127220.033  ops/s
ULIDThroughput.benchmark_randomUUID         thrpt    5   2516984.777 ±  679252.984  ops/s
````

### JS
````
Benchmark                                    Mode  Cnt         Score         Error    Units
ULIDThroughput.benchmark_nextULID_toString  thrpt    5   1115944.060 ±    4050.637  ops/sec
ULIDThroughput.benchmark_randomULID         thrpt    5   1169911.241 ±   14934.966  ops/sec
ULIDThroughput.benchmark_randomUUID         thrpt    5  15785837.058 ± 1873751.818  ops/sec
````

### Native (MacOS)
````
Benchmark                                    Mode  Cnt        Score        Error    Units
ULIDThroughput.benchmark_nextULID_toString  thrpt    5  1197660.899 ± 165852.332  ops/sec
ULIDThroughput.benchmark_randomULID         thrpt    5  1199284.774 ± 165530.799  ops/sec
ULIDThroughput.benchmark_randomUUID         thrpt    5  1251990.054 ± 162325.152  ops/sec
````
