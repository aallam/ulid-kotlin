package com.aallam.ulid

import com.aallam.ulid.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestULID {

    @Test
    fun nextULID_with_random() {
        val ulid = ULID()

        val result = ulid.nextULID()

        assertEquals(26, result.length)
        val timePart = result.substring(0, 10)
        val randomPart = result.substring(10)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertTrue { MinRandomPart <= randomPart }
        assertTrue { MaxRandomPart >= randomPart }
    }

    @Test
    fun nextULID_with_random_0() {
        val random = MockRandom(0)
        val ulid = ULID(random)

        val result = ulid.nextULID()

        assertEquals(0, random.nextLong())
        assertEquals(26, result.length)
        val timePart = result.substring(0, 10)
        val randomPart = result.substring(10)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MinRandomPart, randomPart)
    }

    @Test
    fun nextULID_with_random_minus_1() {
        val random = MockRandom(-1)
        val ulid = ULID(random)

        val result = ulid.nextULID()

        assertEquals(-1, random.nextLong())
        assertEquals(26, result.length)
        val timePart = result.substring(0, 10)
        val randomPart = result.substring(10)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MaxRandomPart, randomPart)
    }
}
