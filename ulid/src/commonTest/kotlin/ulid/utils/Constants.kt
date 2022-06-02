package ulid.utils

const val AllBitsSet = -0x1L
const val PastTimestamp = 1481195424879L
const val PastTimestampPart = "01B3F2133F"

const val MaxTimestamp = 0xFFFFFFFFFFFFL
const val MaxTimestampPart = "7ZZZZZZZZZ"

const val MinTimestamp = 0x0L
const val MinTimestampPart: String = "0000000000"

const val MaxRandomPart = "ZZZZZZZZZZZZZZZZ"
const val MinRandomPart = "0000000000000000"

const val PatternMostSignificantBits = 0x0011_2233_4455_6677L
const val PatternLeastSignificantBits = -0x7766_5544_3322_1101L

val ZeroBytes = ByteArray(16)
val FullBytes = listOf(
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
    0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF
).map { it.toByte() }.toByteArray()
val PatternBytes = listOf(
0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77,
0x88, 0x99, 0xAA, 0xBB, 0xCC, 0xDD, 0xEE, 0xFF
).map { it.toByte() }.toByteArray()

/**
 * Get timestamp and random part of ULID string.
 */
fun partsOf(ulid: String): Pair<String, String> = ulid.substring(0, 10) to ulid.substring(10)
