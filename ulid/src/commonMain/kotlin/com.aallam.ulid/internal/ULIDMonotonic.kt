package com.aallam.ulid.internal

import com.aallam.ulid.ULID

/**
 * Implementation of [ULID.Monotonic] factory.
 */
internal class ULIDMonotonic(private val factory: ULID.Factory) : ULID.Monotonic {

    override fun nextULID(previous: ULID, timestamp: Long): ULID {
        return when (previous.timestamp) {
            timestamp -> previous.increment()
            else -> factory.nextULID(timestamp)
        }
    }

    override fun nextULIDStrict(previous: ULID, timestamp: Long): ULID? {
        val result = nextULID(previous, timestamp)
        return if (result > previous) result else null
    }

    companion object {

        /**
         * Default implementation instance of [ULID.Monotonic].
         */
        val DefaultMonotonic = ULIDMonotonic(ULID)
    }
}
