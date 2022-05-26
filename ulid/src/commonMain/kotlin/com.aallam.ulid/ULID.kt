package com.aallam.ulid

import com.aallam.ulid.internal.requireTimestamp
import com.aallam.ulid.internal.writeCrockford
import kotlinx.datetime.Clock
import kotlin.random.Random

public class ULID(private val random: Random = Random) {

    public fun nextULID(): String {
        val now = Clock.System.now()
        return nextULID(now.toEpochMilliseconds())
    }

    public fun nextULID(timestamp: Long): String {
        requireTimestamp(timestamp)
        val buffer = CharArray(26)
        buffer.writeCrockford(timestamp, 10, 0)
        buffer.writeCrockford(random.nextLong(), 8, 10)
        buffer.writeCrockford(random.nextLong(), 8, 18)
        return buffer.concatToString()
    }
}

