package com.aallam.ulid.internal

/**
 * Writes [Crockford's Base 32](https://www.crockford.com/base32.html).
 */
internal fun CharArray.writeCrockford(value: Long, count: Int, offset: Int) {
    for (i in 0 until count) {
        val index = (value ushr ((count - i - 1) * MaskBits) and Mask.toLong()).toInt()
        set(offset + i, EncodingChars[index])
    }
}
