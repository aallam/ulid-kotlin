package ulid

import ulid.internal.ULIDValue
import ulid.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestULIDMonotonic {

    @Test
    fun test_nextULID() {

        class Input(
            val previousValue: ULID,
            val expectedResult: ULID,
        )

        val inputs = listOf(
            Input(ULIDValue(0, 0), ULIDValue(0, 1)),
            Input(ULIDValue(0, -0x2L), ULIDValue(0, -0x1L)),
            Input(ULIDValue(0, -0x1L), ULIDValue(1, 0)),
            Input(ULIDValue(0xFFFFL, -0x1L), ULIDValue(0, 0)),
        )

        for (input in inputs) {
            input.run {
                val nextULID = ULID.Monotonic.nextULID(previousValue, 0)
                assertEquals(expectedResult, nextULID)
            }
        }
    }

    @Test
    fun test_nextULID_timestamp_mismatch() {
        val previousValue = ULIDValue(0, 0)

        val nextULID1 = ULID.Monotonic.nextULID(previousValue, 1)
        assertEquals(1, nextULID1.timestamp)

        val nextULID2 = ULID.Monotonic.nextULID(previousValue)
        assertTrue { nextULID2.timestamp > 0 }
    }

    @Test
    fun test_nextULIDStrict() {

        class Input(
            val previousValue: ULID,
            val expectedResult: ULID?,
        )

        val inputs = listOf(
            Input(ULIDValue(0, 0), ULIDValue(0, 1)),
            Input(ULIDValue(0, -0x2L), ULIDValue(0, -0x1L)),
            Input(ULIDValue(0, -0x1L), ULIDValue(1L, 0L)),
            Input(ULIDValue(0xFFFFL, -0x1L), null),
        )

        for (input in inputs) {
            input.run {
                val nextULID = ULID.Monotonic.nextULIDStrict(previousValue, 0)
                assertEquals(expectedResult, nextULID)
            }
        }
    }

    @Test
    fun test_nextULIDStrict_timestamp_mismatch() {
        val previousValue = ULIDValue(0, 0)

        val nextULID1 = ULID.Monotonic.nextULIDStrict(previousValue, 1)!!
        assertEquals(1, nextULID1.timestamp)

        val nextULID2 = ULID.Monotonic.nextULIDStrict(previousValue)!!
        assertTrue { nextULID2.timestamp > 0 }
    }

    @Test
    fun test_nextULID_factory() {
        val monotonic = ULID.Monotonic(factory = ULID.Factory())
        val previousValue = ULIDValue(0, 0)
        val result = monotonic.nextULID(previousValue).toString()

        assertEquals(26, result.length)
        val (timePart, randomPart) = partsOf(result)
        assertTrue { PastTimestampPart < timePart }
        assertTrue { MaxTimestampPart >= timePart }
        assertTrue { MinRandomPart <= randomPart }
        assertTrue { MaxRandomPart >= randomPart }
    }
}
