package ulid

import ulid.internal.ULIDValue
import ulid.utils.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestULID {

    @Test
    fun test_toBytes() {
        class Input(val mostSignificantBits: Long, val leastSignificantBits: Long, val expectedData: ByteArray)

        val inputs = listOf(
            Input(0L, 0L, ZeroBytes),
            Input(AllBitsSet, AllBitsSet, FullBytes),
            Input(PatternMostSignificantBits, PatternLeastSignificantBits, PatternBytes),
        )

        for (input in inputs) {
            input.run {
                val ulidValue = ULIDValue(mostSignificantBits, leastSignificantBits)
                val bytes = ulidValue.toBytes()
                assertContentEquals(expectedData, bytes)
            }
        }
    }

    @Test
    fun test_comparable() {
        class Input(
            val mostSignificantBits1: Long,
            val leastSignificantBits1: Long,
            val mostSignificantBits2: Long,
            val leastSignificantBits2: Long,
            val compare: Int,
        )

        val inputs = listOf(
            Input(0L, 0L, 0L, 0L, 0),
            Input(AllBitsSet, AllBitsSet, AllBitsSet, AllBitsSet, 0),
            Input(
                PatternMostSignificantBits, PatternLeastSignificantBits,
                PatternMostSignificantBits, PatternLeastSignificantBits, 0
            ),
            Input(0L, 1L, 0L, 0L, 1),
            Input(1 shl 16, 0L, 0L, 0L, 1),
        )

        for (input in inputs) {
            input.run {
                val value1 = ULIDValue(mostSignificantBits1, leastSignificantBits1)
                val value2 = ULIDValue(mostSignificantBits2, leastSignificantBits2)

                val equals12 = value1 == value2
                val equals21 = value2 == value1
                val compare12 = value1.compareTo(value2)
                val compare21 = value2.compareTo(value1)
                val hash1 = value1.hashCode()
                val hash2 = value2.hashCode()

                assertEquals(equals12, equals21)
                assertEquals(compare12, compare21 * -1)
                when (compare) {
                    0 -> assertEquals(hash1, hash2)
                    else -> {
                        assertEquals(compare12, compare)
                        assertFalse { equals12 }
                    }
                }
            }
        }
    }

    @Test
    fun test_comparable_unsigned_edge_cases() {
        // LSB sign bit: 0x8000000000000000 (Long.MIN_VALUE) should be > 0x7FFFFFFFFFFFFFFF (Long.MAX_VALUE) unsigned
        val a = ULIDValue(100L, Long.MAX_VALUE)
        val b = ULIDValue(100L, Long.MIN_VALUE)
        assertTrue(b > a, "LSB with sign bit set should be greater (unsigned)")
        assertTrue(a < b, "LSB without sign bit should be less (unsigned)")

        // MSB sign bit
        val c = ULIDValue(Long.MAX_VALUE, 0L)
        val d = ULIDValue(Long.MIN_VALUE, 0L)
        assertTrue(d > c, "MSB with sign bit set should be greater (unsigned)")
        assertTrue(c < d, "MSB without sign bit should be less (unsigned)")

        // Both bits set
        val e = ULIDValue(Long.MIN_VALUE, Long.MAX_VALUE)
        val f = ULIDValue(Long.MIN_VALUE, Long.MIN_VALUE)
        assertTrue(f > e, "Same MSB, LSB with sign bit should be greater (unsigned)")

        // All bits set should be the maximum
        val max = ULIDValue(AllBitsSet, AllBitsSet)
        val almostMax = ULIDValue(AllBitsSet, AllBitsSet - 1)
        assertTrue(max > almostMax)
    }

    @Test
    fun test_increment() {
        // Basic increment
        val a = ULIDValue(0L, 0L)
        val b = a.increment()
        assertEquals(ULIDValue(0L, 1L), b)

        // Increment at LSB boundary (Long.MAX_VALUE -> Long.MIN_VALUE in signed, but correct unsigned continuation)
        val c = ULIDValue(0L, Long.MAX_VALUE)
        val d = c.increment()
        assertEquals(ULIDValue(0L, Long.MIN_VALUE), d)
        assertTrue(d > c, "Incremented value should be greater (unsigned)")

        // Increment at LSB = -1 (all bits set) -> carries to MSB
        val e = ULIDValue(0L, -0x1L)
        val f = e.increment()
        assertEquals(ULIDValue(1L, 0L), f)

        // Overflow: when 80-bit random part is maxed out, resets to zero random
        val g = ULIDValue(0xFFFFL, -0x1L)
        val h = g.increment()
        assertEquals(ULIDValue(0L, 0L), h)
    }

    @Test
    fun test_nextULIDStrict_with_lsb_max_value() {
        // When LSB = Long.MAX_VALUE and timestamp matches, increment gives LSB = Long.MIN_VALUE
        // which should be considered greater (unsigned), so nextULIDStrict should return non-null
        val previous = ULIDValue(0L, Long.MAX_VALUE)
        val result = ULID.Monotonic.nextULIDStrict(previous, 0)
        assertEquals(ULIDValue(0L, Long.MIN_VALUE), result)
        assertTrue(result!! > previous, "Incremented value should be greater (unsigned)")
    }

    @Test
    fun test_roundtrip_crockford_encode_decode() {
        // Verify parse(encode(x)) == x for various values
        val values = listOf(
            ULIDValue(0L, 0L),
            ULIDValue(AllBitsSet, AllBitsSet),
            ULIDValue(PatternMostSignificantBits, PatternLeastSignificantBits),
            ULIDValue(Long.MAX_VALUE, Long.MIN_VALUE),
            ULIDValue(Long.MIN_VALUE, Long.MAX_VALUE),
        )

        for (original in values) {
            val encoded = original.toString()
            val decoded = ULID.parseULID(encoded)
            assertEquals(original, decoded, "Round-trip failed for $original")
        }
    }
}
