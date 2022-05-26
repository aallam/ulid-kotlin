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

    public fun fromBytes(data: ByteArray): Value {
        require(data.size == 16) { "data must be 16 bytes in length" }
        var mostSignificantBits: Long = 0
        var leastSignificantBits: Long = 0
        for (i in 0..7) mostSignificantBits = (mostSignificantBits shl 8) or (data[i].toLong() and 0xFF)
        for (i in 8..15) leastSignificantBits = (leastSignificantBits shl 8) or (data[i].toLong() and 0xff)
        return Value(mostSignificantBits, leastSignificantBits)
    }

    public data class Value(
        val mostSignificantBits: Long,
        val leastSignificantBits: Long
    ) : Comparable<Value> {
        public fun timestamp(): Long {
            return mostSignificantBits ushr 16
        }

        public fun toBytes(): ByteArray {
            val bytes = ByteArray(16)
            for (i in 0..7) bytes[i] = (mostSignificantBits shr ((7 - i) * 8) and 0xFFL).toByte()
            for (i in 8..15) bytes[i] = (leastSignificantBits shr ((15 - i) * 8) and 0xFFL).toByte()
            return bytes
        }

        override fun compareTo(other: Value): Int {
            return if (mostSignificantBits < other.mostSignificantBits) -1
            else if (mostSignificantBits > other.mostSignificantBits) 1
            else if (leastSignificantBits < other.leastSignificantBits) -1
            else if (leastSignificantBits > other.leastSignificantBits) 1
            else 0
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
