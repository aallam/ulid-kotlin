package ulid

import ulid.internal.currentTimeMillis
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

/**
 * A thread-safe, stateful monotonic [ULID] generator that internally tracks the previously generated [ULID].
 *
 * Unlike [ULID.Monotonic], callers do not need to manage state themselves.
 * Thread safety is achieved via lock-free compare-and-swap operations.
 */
@OptIn(ExperimentalAtomicApi::class)
public class MonotonicULIDGenerator(
    private val factory: ULID.Factory = ULID,
    private val monotonic: ULID.Monotonic = ULID.Monotonic(factory),
) {

    private val previousRef = AtomicReference<ULID?>(null)

    /**
     * Generate the next monotonic [ULID].
     *
     * If the timestamp matches the previous ULID's timestamp, the random part is incremented.
     * If an overflow happens, the random part is reset to zero.
     *
     * @param timestamp timestamp epoch in milliseconds
     */
    public fun nextULID(timestamp: Long = currentTimeMillis()): ULID {
        while (true) {
            val prev = previousRef.load()
            val result = if (prev == null) factory.nextULID(timestamp) else monotonic.nextULID(prev, timestamp)
            if (previousRef.compareAndSet(prev, result)) return result
        }
    }

    /**
     * Generate the next strict monotonic [ULID], or `null` if an overflow happened.
     *
     * @param timestamp timestamp epoch in milliseconds
     */
    public fun nextULIDStrict(timestamp: Long = currentTimeMillis()): ULID? {
        while (true) {
            val prev = previousRef.load()
            if (prev == null) {
                val result = factory.nextULID(timestamp)
                if (previousRef.compareAndSet(null, result)) return result
                continue
            }
            val result = monotonic.nextULIDStrict(prev, timestamp)
            val newPrev = result ?: prev
            if (previousRef.compareAndSet(prev, newPrev)) return result
        }
    }
}
