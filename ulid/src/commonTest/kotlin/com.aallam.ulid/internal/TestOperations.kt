package com.aallam.ulid.internal

import com.aallam.ulid.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TestOperations {

    @Test
    fun testWriteCrockford() {
        class Input(
            val inputValue: Long,
            val bufferSize: Int,
            val length: Int,
            val offset: Int,
            val expectedResult: String
        )

        val inputs = listOf(
            Input(0L, 1, 1, 0, "0"),
            Input(1L, 1, 1, 0, "1"),
            Input(2L, 1, 1, 0, "2"),
            Input(3L, 1, 1, 0, "3"),
            Input(4L, 1, 1, 0, "4"),
            Input(5L, 1, 1, 0, "5"),
            Input(6L, 1, 1, 0, "6"),
            Input(7L, 1, 1, 0, "7"),
            Input(8L, 1, 1, 0, "8"),
            Input(9L, 1, 1, 0, "9"),
            Input(10L, 1, 1, 0, "A"),
            Input(11L, 1, 1, 0, "B"),
            Input(12L, 1, 1, 0, "C"),
            Input(13L, 1, 1, 0, "D"),
            Input(14L, 1, 1, 0, "E"),
            Input(15L, 1, 1, 0, "F"),
            Input(16L, 1, 1, 0, "G"),
            Input(17L, 1, 1, 0, "H"),
            Input(18L, 1, 1, 0, "J"),
            Input(19L, 1, 1, 0, "K"),
            Input(20L, 1, 1, 0, "M"),
            Input(21L, 1, 1, 0, "N"),
            Input(22L, 1, 1, 0, "P"),
            Input(23L, 1, 1, 0, "Q"),
            Input(24L, 1, 1, 0, "R"),
            Input(25L, 1, 1, 0, "S"),
            Input(26L, 1, 1, 0, "T"),
            Input(27L, 1, 1, 0, "V"),
            Input(28L, 1, 1, 0, "W"),
            Input(29L, 1, 1, 0, "X"),
            Input(30L, 1, 1, 0, "Y"),
            Input(31L, 1, 1, 0, "Z"),
            Input(32L, 1, 1, 0, "0"),
            Input(32L, 2, 2, 0, "10"),
            Input(0L, 0, 0, 0, ""),
            Input(0L, 2, 0, 0, "##"),
            Input(0L, 13, 13, 0, "0000000000000"),
            Input(194L, 2, 2, 0, "62"),
            Input(45_678L, 4, 4, 0, "1CKE"),
            Input(393_619L, 4, 4, 0, "C0CK"),
            Input(398_373L, 4, 4, 0, "C515"),
            Input(421_562L, 4, 4, 0, "CVNT"),
            Input(456_789L, 4, 4, 0, "DY2N"),
            Input(519_571L, 4, 4, 0, "FVCK"),
            Input(3_838_385_658_376_483L, 11, 11, 0, "3D2ZQ6TVC93"),
            Input(0x1FL, 1, 1, 0, "Z"),
            Input(0x1FL shl 5, 1, 1, 0, "0"),
            Input(0x1FL shl 5, 2, 2, 0, "Z0"),
            Input(0x1FL shl 10, 1, 1, 0, "0"),
            Input(0x1FL shl 10, 2, 2, 0, "00"),
            Input(0x1FL shl 10, 3, 3, 0, "Z00"),
            Input(0x1FL shl 15, 3, 3, 0, "000"),
            Input(0x1FL shl 15, 4, 4, 0, "Z000"),
            Input(0x1FL shl 55, 13, 13, 0, "0Z00000000000"),
            Input(0x1FL shl 60, 13, 13, 0, "F000000000000"),
            Input(AllBitsSet, 13, 13, 0, "FZZZZZZZZZZZZ"),
            Input(PastTimestamp, 10, 10, 0, PastTimestampPart),
            Input(MaxTimestamp, 10, 10, 0, MaxTimestampPart),
            Input(45_678L, 8, 4, 3, "###1CKE#"),
            Input(45_678L, 8, 4, 4, "####1CKE"),
        )

        for (input in inputs) {
            input.run {
                val buffer = CharArray(bufferSize) { '#' }
                buffer.writeCrockford(inputValue, length, offset)
                val result = buffer.concatToString()
                assertEquals(expectedResult, result)
            }
        }
    }
}
