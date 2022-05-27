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
internal fun StringBuilder.append(value: Long, count: Int) {
    for (i in count - 1 downTo 0) {
        val index = (value ushr (i * MaskBits) and Mask).toInt()
        append(EncodingChars[index])
    }
}
