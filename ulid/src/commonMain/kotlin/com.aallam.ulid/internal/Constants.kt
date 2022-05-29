package com.aallam.ulid.internal

internal const val Mask: Long = 0x1F
internal const val MaskBits = 5
internal const val TimestampOverflowMask = -0x1000000000000L
internal const val TimestampMsbMask = -0x10000L
internal const val RandomMsbMask = 0xFFFFL
