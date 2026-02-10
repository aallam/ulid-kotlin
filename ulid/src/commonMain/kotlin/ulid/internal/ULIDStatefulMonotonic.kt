package ulid.internal

import ulid.ULID
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

/**
 * Implementation of [ULID.StatefulMonotonic].
 */
@OptIn(ExperimentalAtomicApi::class)
internal class ULIDStatefulMonotonic(
    private val factory: ULID.Factory,
    private val monotonic: ULID.Monotonic,
) : ULID.StatefulMonotonic {

    private val previousRef = AtomicReference<ULID?>(null)

    override fun nextULID(timestamp: Long): ULID {
        while (true) {
            val prev = previousRef.load()
            val result = if (prev == null) factory.nextULID(timestamp) else monotonic.nextULID(prev, timestamp)
            if (previousRef.compareAndSet(prev, result)) return result
        }
    }

    override fun nextULIDStrict(timestamp: Long): ULID? {
        while (true) {
            val prev = previousRef.load()
            val result = if (prev == null) factory.nextULID(timestamp) else monotonic.nextULIDStrict(prev, timestamp)
            if (previousRef.compareAndSet(prev, result ?: prev)) return result
        }
    }

    // Delegate Factory methods
    override fun randomULID(timestamp: Long): String = factory.randomULID(timestamp)
    override fun fromBytes(data: ByteArray): ULID = factory.fromBytes(data)
    override fun parseULID(ulidString: String): ULID = factory.parseULID(ulidString)
}
