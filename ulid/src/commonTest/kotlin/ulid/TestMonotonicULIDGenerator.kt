package ulid

import ulid.utils.MockRandom
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestMonotonicULIDGenerator {

    @Test
    fun test_first_call_generates_new_ulid() {
        val generator = ULID.MonotonicGenerator()
        val ulid = generator.nextULID()
        assertTrue(ulid.timestamp > 0)
    }

    @Test
    fun test_same_timestamp_increments() {
        val generator = ULID.MonotonicGenerator(factory = ULID.Factory(MockRandom(0)))
        val first = generator.nextULID(timestamp = 1000)
        val second = generator.nextULID(timestamp = 1000)
        assertEquals(first.timestamp, second.timestamp)
        assertTrue(second > first, "Second ULID should be greater than first")
        assertEquals(first.leastSignificantBits + 1, second.leastSignificantBits)
    }

    @Test
    fun test_different_timestamp_generates_new() {
        val generator = ULID.MonotonicGenerator()
        val first = generator.nextULID(timestamp = 1000)
        val second = generator.nextULID(timestamp = 1001)
        assertEquals(1000L, first.timestamp)
        assertEquals(1001L, second.timestamp)
    }

    @Test
    fun test_strict_first_call() {
        val generator = ULID.MonotonicGenerator()
        val ulid = generator.nextULIDStrict()
        assertNotNull(ulid)
        assertTrue(ulid.timestamp > 0)
    }

    @Test
    fun test_strict_same_timestamp_increments() {
        val generator = ULID.MonotonicGenerator(factory = ULID.Factory(MockRandom(0)))
        val first = generator.nextULIDStrict(timestamp = 1000)
        assertNotNull(first)
        val second = generator.nextULIDStrict(timestamp = 1000)
        assertNotNull(second)
        assertTrue(second > first)
    }

    @Test
    fun test_strict_overflow_returns_null() {
        val generator = ULID.MonotonicGenerator(factory = ULID.Factory(MockRandom(-1)))
        // First call: generates ULID with all random bits set (MSB lower 16 bits = 0xFFFF, LSB = -1)
        val first = generator.nextULIDStrict(timestamp = 1000)
        assertNotNull(first)
        // Second call: overflow, should return null
        val second = generator.nextULIDStrict(timestamp = 1000)
        assertNull(second)
    }

    @Test
    fun test_multiple_increments() {
        val generator = ULID.MonotonicGenerator(factory = ULID.Factory(MockRandom(0)))
        val ulids = (1..10).map { generator.nextULID(timestamp = 500) }
        for (i in 1 until ulids.size) {
            assertTrue(ulids[i] > ulids[i - 1], "ULID at index $i should be greater than previous")
        }
    }
}
