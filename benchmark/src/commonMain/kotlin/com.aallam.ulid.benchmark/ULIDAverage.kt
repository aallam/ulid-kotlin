package com.aallam.ulid.benchmark

import com.aallam.ulid.ULID
import kotlinx.benchmark.*

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.NANOSECONDS)
class ULIDAverage {

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
