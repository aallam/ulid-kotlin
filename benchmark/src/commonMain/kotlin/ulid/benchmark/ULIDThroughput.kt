package ulid.benchmark

import kotlinx.benchmark.*
import ulid.ULID

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(BenchmarkTimeUnit.SECONDS)
class ULIDThroughput {

    @Benchmark
    fun benchmark_randomULID(): String {
        return ULID.randomULID()
    }

    @Benchmark
    fun benchmark_nextULID_toString(): String {
        return ULID.nextULID().toString()
    }

    @Benchmark
    fun benchmark_randomUUID(): String {
        return randomUUID()
    }
}
