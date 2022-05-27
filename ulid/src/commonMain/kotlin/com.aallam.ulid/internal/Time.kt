package com.aallam.ulid.internal

import kotlinx.datetime.Clock

/**
 * Require valid timestamp.
 */
internal fun requireTimestamp(timestamp: Long) {
    require((timestamp and TimestampOverflowMask) == 0L) { "ULID does not support timestamps after +10889-08-02T05:31:50.655Z!" }
}

/**
 * Current epoch time in milliseconds
 */
internal fun currentTimeMillis() = Clock.System.now().toEpochMilliseconds()
