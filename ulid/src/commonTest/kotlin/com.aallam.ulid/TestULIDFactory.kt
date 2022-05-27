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

    @Test
    fun test_parseULID_and_toString() {
        class Input(
            val ulidString: String, val expectedTimestamp: Long
        )

        val inputs = listOf(
            Input(PastTimestampPart + "0000000000000000", PastTimestamp),
            Input(PastTimestampPart + "ZZZZZZZZZZZZZZZZ", PastTimestamp),
            Input(PastTimestampPart + "123456789ABCDEFG", PastTimestamp),
            Input(PastTimestampPart + "1000000000000000", PastTimestamp),
            Input(PastTimestampPart + "1000000000000001", PastTimestamp),
            Input(PastTimestampPart + "0001000000000001", PastTimestamp),
            Input(PastTimestampPart + "0100000000000001", PastTimestamp),
            Input(PastTimestampPart + "0000000000000001", PastTimestamp),
            Input(MinTimestampPart + "123456789ABCDEFG", MinTimestamp),
            Input(MaxTimestampPart + "123456789ABCDEFG", MaxTimestamp),
        )

        for (input in inputs) {
            input.run {
                val ulid = ULID.parseULID(ulidString)
                assertEquals(ulidString, ulid.toString())
                assertEquals(expectedTimestamp, ulid.timestamp)
            }
        }
    }

    @Test
    fun test_parseULID_toString_invalid() {
        class Input(
            val ulidString: String,
            val expectedString: String,
            val expectedTimestamp: Long,
        )

        val inputs = listOf(
            Input(PastTimestampPart + "0l00000000000000", PastTimestampPart + "0100000000000000", PastTimestamp),
            Input(PastTimestampPart + "0L00000000000000", PastTimestampPart + "0100000000000000", PastTimestamp),
            Input(PastTimestampPart + "0i00000000000000", PastTimestampPart + "0100000000000000", PastTimestamp),
            Input(PastTimestampPart + "0I00000000000000", PastTimestampPart + "0100000000000000", PastTimestamp),
            Input(PastTimestampPart + "0o00000000000000", PastTimestampPart + "0000000000000000", PastTimestamp),
            Input(PastTimestampPart + "0O00000000000000", PastTimestampPart + "0000000000000000", PastTimestamp),
        )

        for (input in inputs) {
            input.run {
                val ulid = ULID.parseULID(ulidString)
                assertEquals(expectedString, ulid.toString())
                assertEquals(expectedTimestamp, ulid.timestamp)
            }
        }
    }

    @Test
    fun test_parseULID_fails() {
        val inputs = listOf(
            "0000000000000000000000000",
            "000000000000000000000000000",
            "80000000000000000000000000",
        )

        for (input in inputs) {
            assertFailsWith<IllegalArgumentException> { ULID.parseULID(input) }
        }
    }

    private fun partsOf(ulid: String): Pair<String, String> = ulid.substring(0, 10) to ulid.substring(10)
}
