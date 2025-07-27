package ulid.internal

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Require valid timestamp.
 */
internal fun requireTimestamp(timestamp: Long) {
    require((timestamp and TimestampOverflowMask) == 0L) { "ULID does not support timestamps after +10889-08-02T05:31:50.655Z!" }
}

/**
 * Current epoch time in milliseconds
 */
@OptIn(ExperimentalTime::class)
internal fun currentTimeMillis() = Clock.System.now().toEpochMilliseconds()
