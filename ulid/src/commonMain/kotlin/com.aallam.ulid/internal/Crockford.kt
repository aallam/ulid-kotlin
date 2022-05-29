package com.aallam.ulid.internal

/**
 * [Crockford's Base 32](https://www.crockford.com/base32.html).
 */
internal fun CharArray.write(value: Long, count: Int, offset: Int) {
    for (i in 0 until count) {
        val index = (value ushr ((count - i - 1) * MaskBits) and Mask).toInt()
        set(offset + i, EncodingChars[index])
    }
}

/**
 * [Crockford's Base 32](https://www.crockford.com/base32.html).
 */
internal fun String.parseCrockford(): Long {
    require(length <= 12) { "input length must not exceed 12 but was $length!" }
    var result: Long = 0
    for (i in indices) {
        val current = get(i)
        var value: Byte = -1
        if (current.code < DecodingChars.size) {
            value = DecodingChars[current.code]
        }
        require(value >= 0) { "Illegal character '$current'!" }
        result = result or (value.toLong() shl (length - 1 - i) * MaskBits)
    }
    return result
}

/**
 * Crockford's base 32 encoding symbols.
 *
 * [Specification](https://www.crockford.com/base32.html).
 */
private val EncodingChars = charArrayOf(
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
    'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X',
    'Y', 'Z'
)

/**
 * Crockford's base 32 decoding symbols. `-1` when not supported.
 */
private val DecodingChars = byteArrayOf(
    -1, -1, -1, -1, -1, -1, -1, -1,  // 0
    -1, -1, -1, -1, -1, -1, -1, -1,  // 8
    -1, -1, -1, -1, -1, -1, -1, -1,  // 16
    -1, -1, -1, -1, -1, -1, -1, -1,  // 24
    -1, -1, -1, -1, -1, -1, -1, -1,  // 32
    -1, -1, -1, -1, -1, -1, -1, -1,  // 40
    0, 1, 2, 3, 4, 5, 6, 7,          // 48
    8, 9, -1, -1, -1, -1, -1, -1,    // 56
    -1, 10, 11, 12, 13, 14, 15, 16,  // 64
    17, 1, 18, 19, 1, 20, 21, 0,     // 72
    22, 23, 24, 25, 26, -1, 27, 28,  // 80
    29, 30, 31, -1, -1, -1, -1, -1,  // 88
    -1, 10, 11, 12, 13, 14, 15, 16,  // 96
    17, 1, 18, 19, 1, 20, 21, 0,     // 104
    22, 23, 24, 25, 26, -1, 27, 28,  // 112
    29, 30, 31                       // 120
)
