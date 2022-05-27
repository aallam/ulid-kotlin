package com.aallam.ulid.internal

import com.aallam.ulid.ULID

/**
 * Implementation of [ULID.Monotonic] factory.
 */
internal class ULIDMonotonic(private val factory: ULID.Factory) : ULID.Monotonic {

    override fun nextULID(previousULID: ULID, timestamp: Long): ULID {
        return when (previousULID.timestamp) {
            timestamp -> previousULID.increment()
            else -> factory.nextULID(timestamp)
        }
    }

    override fun nextULIDStrict(previousULID: ULID, timestamp: Long): ULID? {
        val result = nextULID(previousULID, timestamp)
        return if (result > previousULID) result else null
    }

    companion object {
        val DefaultMonotonic = ULIDMonotonic(ULID)
    }
}
