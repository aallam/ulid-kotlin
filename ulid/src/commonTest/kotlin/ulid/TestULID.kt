package ulid

import ulid.internal.ULIDValue
import ulid.utils.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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
}
