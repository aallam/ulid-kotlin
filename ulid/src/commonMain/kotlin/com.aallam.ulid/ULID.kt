package com.aallam.ulid

import com.aallam.ulid.internal.Crockford
import com.aallam.ulid.internal.requireTimestamp
import kotlinx.datetime.Clock
import kotlin.random.Random

public class ULID(private val random: Random = Random) {

    private val crockford = Crockford()

    public fun nextULID(): String {
        val now = Clock.System.now()
        return nextULID(now.toEpochMilliseconds())
    }

    public fun nextULID(timestamp: Long): String {
        requireTimestamp(timestamp)
        val buffer = CharArray(26)
        with(crockford) {
            buffer.write(timestamp, 10, 0)
            buffer.write(random.nextLong(), 8, 10)
            buffer.write(random.nextLong(), 8, 18)
        }
        return buffer.concatToString()
    }

    public companion object
}
