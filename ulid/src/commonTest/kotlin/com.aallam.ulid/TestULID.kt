package com.aallam.ulid

import com.aallam.ulid.utils.*
import kotlin.test.*

class TestULID {

    @Test
    fun test_nextULID() {
        val ulid = ULID()

        val result = ulid.nextULID()

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
        val ulid = ULID(random)

        val result = ulid.nextULID()

        assertEquals(0, random.nextLong())
        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MinRandomPart, randomPart)
    }

    @Test
    fun test_nextULID_with_random_minus_1() {
        val random = MockRandom(-1)
        val ulid = ULID(random)

        val result = ulid.nextULID()

        assertEquals(-1, random.nextLong())
        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MaxRandomPart, randomPart)
    }

    @Test
    fun test_nextULID_invalid_timestamp() {
        val ulid = ULID()

        assertFailsWith<IllegalArgumentException> {
            ulid.nextULID(0x0001000000000000L)
        }
    }

    @Test
    fun test_nextValue() {
        val ulid = ULID()

        val result = ulid.nextValue().toString()

        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertTrue { MinRandomPart <= randomPart }
        assertTrue { MaxRandomPart >= randomPart }
    }

    @Test
    fun test_nextValue_with_random_0() {
        val random = MockRandom(0)
        val ulid = ULID(random)

        val result = ulid.nextValue().toString()

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
        val ulid = ULID(random)

        val result = ulid.nextValue().toString()

        assertEquals(-1, random.nextLong())
        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertEquals(MaxRandomPart, randomPart)
    }

    @Test
    fun test_nextValue_invalid_timestamp() {
        val ulid = ULID()

        assertFailsWith<IllegalArgumentException> {
            ulid.nextValue(0x0001000000000000L)
        }
    }

    @Test
    fun test_ulid_value_toBytes() {
        class Input(val mostSignificantBits: Long, val leastSignificantBits: Long, val expectedData: ByteArray)

        val inputs = listOf(
            Input(0L, 0L, ZeroBytes),
            Input(AllBitsSet, AllBitsSet, FullBytes),
            Input(PatternMostSignificantBits, PatternLeastSignificantBits, PatternBytes),
        )

        for (input in inputs) {
            input.run {
                val ulidValue = ULID.Value(mostSignificantBits, leastSignificantBits)
                val bytes = ulidValue.toBytes()
                assertContentEquals(expectedData, bytes)
            }
        }
    }

    private fun partsOf(ulid: String): Pair<String, String> = ulid.substring(0, 10) to ulid.substring(10)
}
