package com.aallam.ulid.internal

import com.aallam.ulid.ULID

/**
 * Implementation of [ULID.Value].
 */
internal data class ULIDValue(
    override val mostSignificantBits: Long,
    override val leastSignificantBits: Long,
) : ULID.Value {

    override val timestamp: Long
        get() = mostSignificantBits ushr 16

    override fun toBytes(): ByteArray {
        val bytes = ByteArray(16)
        for (i in 0..7) bytes[i] = (mostSignificantBits shr ((7 - i) * 8) and 0xFFL).toByte()
        for (i in 8..15) bytes[i] = (leastSignificantBits shr ((15 - i) * 8) and 0xFFL).toByte()
        return bytes
    }

    override fun compareTo(other: ULID.Value): Int {
        return if (mostSignificantBits < other.mostSignificantBits) -1
        else if (mostSignificantBits > other.mostSignificantBits) 1
        else if (leastSignificantBits < other.leastSignificantBits) -1
        else if (leastSignificantBits > other.leastSignificantBits) 1
        else 0
    }

    override fun toString(): String {
        val buffer = CharArray(26)
        with(Crockford) {
            buffer.write(timestamp, 10, 0)
            var value = mostSignificantBits and 0xFFFFL shl 24
            val interim = leastSignificantBits ushr 40
            value = value or interim
            buffer.write(value, 8, 10)
            buffer.write(leastSignificantBits, 8, 18)
        }
        return buffer.concatToString()
    }
}