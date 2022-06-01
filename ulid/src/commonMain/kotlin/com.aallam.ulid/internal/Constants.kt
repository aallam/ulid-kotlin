package com.aallam.ulid.internal

internal const val Mask5Bits = 0x1FL // 32 encoding value (0..31)
internal const val Mask5BitsCount = 5 // 5 bits, needed to encode 32 value
internal const val TimestampOverflowMask = -0x1_0000_0000_0000L // maximum timestamp (32 high + 16 low bits)
internal const val TimestampMsbMask = -0x1_0000L
internal const val Mask16Bits = 0xFFFFL // 16 bits mask
internal const val Mask8Bits = 0xFFL // 8 bits mask
