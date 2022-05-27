package com.aallam.ulid.internal.utils

internal const val Mask: Long = 0x1F
internal const val MaskBits = 5
internal const val TimestampOverflowMask = -0x1000000000000L
internal const val TimestampMsbMask = -0x10000L
internal const val RandomMsbMask = 0xFFFFL

internal val EncodingChars = charArrayOf(
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
    'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X',
    'Y', 'Z'
)

internal val DecodingChars = byteArrayOf(
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
