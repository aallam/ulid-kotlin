package com.aallam.ulid.internal

import com.aallam.ulid.ULID
import kotlinx.datetime.Clock
import kotlin.random.Random

/**
 * Implementation of [ULID.Factory].
 *
 * @param random random number generator
 */
internal class ULIDFactory(private val random: Random = Random) : ULID.Factory {

    override fun nextULIDString(): String {
        val now = Clock.System.now()
        return nextULIDString(now.toEpochMilliseconds())
    }

    override fun nextULIDString(timestamp: Long): String {
        requireTimestamp(timestamp)
        val buffer = CharArray(26)
        buffer.write(timestamp, 10, 0)
        buffer.write(random.nextLong(), 8, 10)
        buffer.write(random.nextLong(), 8, 18)
        return buffer.concatToString()
    }

    override fun nextULID(): ULID {
        val now = Clock.System.now()
        return nextULID(now.toEpochMilliseconds())
    }

    override fun nextULID(timestamp: Long): ULID {
        requireTimestamp(timestamp)
        var mostSignificantBits = random.nextLong()
        val leastSignificantBits = random.nextLong()
        mostSignificantBits = mostSignificantBits and 0xFFFFL
        mostSignificantBits = mostSignificantBits or (timestamp shl 16)
        return ULIDValue(mostSignificantBits, leastSignificantBits)
    }

    override fun fromBytes(data: ByteArray): ULID {
        require(data.size == 16) { "data must be 16 bytes in length" }
        var mostSignificantBits: Long = 0
        var leastSignificantBits: Long = 0
        for (i in 0..7) mostSignificantBits = (mostSignificantBits shl 8) or (data[i].toLong() and 0xFF)
        for (i in 8..15) leastSignificantBits = (leastSignificantBits shl 8) or (data[i].toLong() and 0xff)
        return ULIDValue(mostSignificantBits, leastSignificantBits)
    }

    companion object {

        /**
         * Default implementation instance of [ULID.Factory].
         */
        val Default = ULIDFactory()
    }
}
