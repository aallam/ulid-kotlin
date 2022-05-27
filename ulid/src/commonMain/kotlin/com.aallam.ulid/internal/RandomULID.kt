package com.aallam.ulid.internal

import com.aallam.ulid.ULID
import com.aallam.ulid.internal.utils.requireTimestamp
import kotlinx.datetime.Clock
import kotlin.random.Random

/**
 * Implementation of [ULID].
 */
internal class RandomULID(private val random: Random) : ULID {

    override fun nextULID(): String {
        val now = Clock.System.now()
        return nextULID(now.toEpochMilliseconds())
    }

    override fun nextULID(timestamp: Long): String {
        requireTimestamp(timestamp)
        val buffer = CharArray(26)
        with(Crockford) {
            buffer.write(timestamp, 10, 0)
            buffer.write(random.nextLong(), 8, 10)
            buffer.write(random.nextLong(), 8, 18)
        }
        return buffer.concatToString()
    }

    override fun nextValue(): ULID.Value {
        val now = Clock.System.now()
        return nextValue(now.toEpochMilliseconds())
    }

    override fun nextValue(timestamp: Long): ULID.Value {
        requireTimestamp(timestamp)
        var mostSignificantBits = random.nextLong()
        val leastSignificantBits = random.nextLong()
        mostSignificantBits = mostSignificantBits and 0xFFFFL
        mostSignificantBits = mostSignificantBits or (timestamp shl 16)
        return ULID.Value(mostSignificantBits, leastSignificantBits)
    }

    override fun fromBytes(data: ByteArray): ULID.Value {
        require(data.size == 16) { "data must be 16 bytes in length" }
        var mostSignificantBits: Long = 0
        var leastSignificantBits: Long = 0
        for (i in 0..7) mostSignificantBits = (mostSignificantBits shl 8) or (data[i].toLong() and 0xFF)
        for (i in 8..15) leastSignificantBits = (leastSignificantBits shl 8) or (data[i].toLong() and 0xff)
        return ULID.Value(mostSignificantBits, leastSignificantBits)
    }

    companion object {
        val Default = RandomULID(Random)
    }
}
