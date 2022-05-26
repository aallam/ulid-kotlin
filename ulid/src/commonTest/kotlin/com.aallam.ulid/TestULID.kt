package com.aallam.ulid

import com.aallam.ulid.utils.MaxTimestampPart
import com.aallam.ulid.utils.MockRandom
import com.aallam.ulid.utils.PastTimestampPart
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestULID {

    @Test
    fun nextULID_with_random_0() {
        val random = MockRandom()
        val ulid = ULID(random)

        val result = ulid.nextULID()

        assertEquals(0, random.nextInt())
        assertEquals(26, result.length)
        val timePart = result.substring(0, 10)
        val randomPart = result.substring(10)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals("0000000000000000", randomPart)
    }
}
