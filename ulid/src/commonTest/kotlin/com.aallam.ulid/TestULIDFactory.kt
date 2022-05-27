package com.aallam.ulid

import com.aallam.ulid.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TestULIDFactory {

    @Test
    fun test_nextULIDString() {
        val result = ULID.nextULIDString()

        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertTrue { MinRandomPart <= randomPart }
        assertTrue { MaxRandomPart >= randomPart }
    }

    @Test
    fun test_nextULIDString_with_random_0() {
        val random = MockRandom(0)
        val factory = ULID.Factory(random)

        val result = factory.nextULIDString()

        assertEquals(0, random.nextLong())
        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MinRandomPart, randomPart)
    }

    @Test
    fun test_nextULIDString_with_random_minus_1() {
        val random = MockRandom(-1)
        val factory = ULID.Factory(random)

        val result = factory.nextULIDString()

        assertEquals(-1, random.nextLong())
        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MaxRandomPart, randomPart)
    }

    @Test
    fun test_nextULIDString_invalid_timestamp() {
        assertFailsWith<IllegalArgumentException> {
            ULID.nextULIDString(0x0001000000000000L)
        }
    }

    @Test
    fun test_nextULID() {
        val result = ULID.nextULID().toString()

        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertTrue { MinRandomPart <= randomPart }
        assertTrue { MaxRandomPart >= randomPart }
    }

    @Test
    fun test_nextULID_with_random_0() {
        val random = MockRandom(0)
        val factory = ULID.Factory(random)

        val result = factory.nextULID().toString()

        assertEquals(0, random.nextLong())
        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MinRandomPart, randomPart)
    }

    @Test
    fun test_nextValue_with_random_minus_1() {
        val random = MockRandom(-1)
        val factory = ULID.Factory(random)

        val result = factory.nextULID().toString()

        assertEquals(-1, random.nextLong())
        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MaxRandomPart, randomPart)
    }

    @Test
    fun test_nextValue_invalid_timestamp() {
        assertFailsWith<IllegalArgumentException> {
            ULID.nextULID(0x0001000000000000L)
        }
    }

    @Test
    fun test_fromBytes() {
        class Input(
            val data: ByteArray,
            val mostSignificantBits: Long,
            val leastSignificantBits: Long,
        )

        val inputs = listOf(
            Input(ZeroBytes, 0L, 0L),
            Input(FullBytes, AllBitsSet, AllBitsSet),
            Input(PatternBytes, PatternMostSignificantBits, PatternLeastSignificantBits),
        )

        for (input in inputs) {
            input.run {
                val ulid = ULID.fromBytes(data)
                assertEquals(ulid.mostSignificantBits, mostSignificantBits)
                assertEquals(ulid.leastSignificantBits, leastSignificantBits)
            }
        }
    }

    @Test
    fun test_fromBytes_fails() {
        assertFailsWith<IllegalArgumentException> { ULID.fromBytes(ByteArray(15)) }
        assertFailsWith<IllegalArgumentException> { ULID.fromBytes(ByteArray(17)) }
    }

    private fun partsOf(ulid: String): Pair<String, String> = ulid.substring(0, 10) to ulid.substring(10)
}
