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
