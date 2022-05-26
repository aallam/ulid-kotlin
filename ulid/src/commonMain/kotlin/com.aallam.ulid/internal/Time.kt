package com.aallam.ulid.internal

internal fun requireTimestamp(timestamp: Long) {
    require((timestamp and TimestampOverflowMask) == 0L) { "ULID does not support timestamps after +10889-08-02T05:31:50.655Z!" }
}
