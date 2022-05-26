package com.aallam.ulid

import com.aallam.ulid.internal.Crockford
import com.aallam.ulid.internal.requireTimestamp
import kotlinx.datetime.Clock
import kotlin.random.Random

public class ULID(private val random: Random = Random) {

    public fun nextULID(): String {
        val now = Clock.System.now()
        return nextULID(now.toEpochMilliseconds())
    }

    public fun nextULID(timestamp: Long): String {
        requireTimestamp(timestamp)
        val buffer = CharArray(26)
        with(Crockford) {
            buffer.write(timestamp, 10, 0)
            buffer.write(random.nextLong(), 8, 10)
            buffer.write(random.nextLong(), 8, 18)
        }
        return buffer.concatToString()
    }

    public fun nextValue(): Value {
        val now = Clock.System.now()
        return nextValue(now.toEpochMilliseconds())
    }

    public fun nextValue(timestamp: Long): Value {
        requireTimestamp(timestamp)
        var mostSignificantBits = random.nextLong()
        val leastSignificantBits = random.nextLong()
        mostSignificantBits = mostSignificantBits and 0xFFFFL
        mostSignificantBits = mostSignificantBits or (timestamp shl 16)
        return Value(mostSignificantBits, leastSignificantBits)
    }

    public data class Value(
        private val mostSignificantBits: Long,
        private val leastSignificantBits: Long
    ) {
        public fun timestamp(): Long {
            return mostSignificantBits ushr 16
        }

        override fun toString(): String {
            val buffer = CharArray(26)
            with(Crockford) {
                buffer.write(timestamp(), 10, 0)
                var value = mostSignificantBits and 0xFFFFL shl 24
                val interim = leastSignificantBits ushr 40
                value = value or interim
                buffer.write(value, 8, 10)
                buffer.write(leastSignificantBits, 8, 18)
            }
            return buffer.concatToString()
        }
    }

    public companion object
}
