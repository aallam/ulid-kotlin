package com.aallam.ulid.internal

import com.aallam.ulid.ULID
import kotlin.random.Random

/**
 * Implementation of [ULID.Factory].
 *
 * @param random random number generator
 */
internal class ULIDFactory(private val random: Random = Random) : ULID.Factory {

    override fun randomULID(timestamp: Long): String {
        requireTimestamp(timestamp)
        val buffer = CharArray(26)
        buffer.write(timestamp, 10, 0)
        buffer.write(random.nextLong(), 8, 10)
        buffer.write(random.nextLong(), 8, 18)
        return buffer.concatToString()
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
        for (i in 8..15) leastSignificantBits = (leastSignificantBits shl 8) or (data[i].toLong() and 0xFF)
        return ULIDValue(mostSignificantBits, leastSignificantBits)
    }

    override fun parseULID(ulidString: String): ULID {
        require(ulidString.length == 26) { "ulid string must be exactly 26 chars long" }

        val timeString = ulidString.substring(0, 10)
        val time = timeString.parseCrockford()
        require(time and TimestampOverflowMask == 0L) { "ulid string must not exceed '7ZZZZZZZZZZZZZZZZZZZZZZZZZ'!" }

        val part1String = ulidString.substring(10, 18)
        val part2String = ulidString.substring(18)
        val part1 = part1String.parseCrockford()
        val part2 = part2String.parseCrockford()

        val most = (time shl 16) or (part1 ushr 24)
        val least = part2 or (part1 shl 40)
        return ULIDValue(most, least)
    }

    companion object {

        /**
         * Default implementation instance of [ULID.Factory].
         */
        val Default = ULIDFactory()
    }
}
