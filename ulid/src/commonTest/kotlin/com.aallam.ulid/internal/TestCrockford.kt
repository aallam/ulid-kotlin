package com.aallam.ulid.internal

import com.aallam.ulid.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestCrockford {

    @Test
    fun testWrite() {
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
                buffer.write(inputValue, length, offset)
                val result = buffer.concatToString()
                assertEquals(expectedResult, result)
            }
        }
    }

    @Test
    fun testParse() {

        class Input(
            val string: String,
            val expectedResult: Long,
        )

        val inputs = listOf(
            Input("0", 0L),
            Input("O", 0L),
            Input("o", 0L),
            Input("1", 1L),
            Input("i", 1L),
            Input("I", 1L),
            Input("l", 1L),
            Input("L", 1L),
            Input("2", 2L),
            Input("3", 3L),
            Input("4", 4L),
            Input("5", 5L),
            Input("6", 6L),
            Input("7", 7L),
            Input("8", 8L),
            Input("9", 9L),
            Input("A", 10L),
            Input("a", 10L),
            Input("B", 11L),
            Input("b", 11L),
            Input("C", 12L),
            Input("c", 12L),
            Input("D", 13L),
            Input("d", 13L),
            Input("E", 14L),
            Input("e", 14L),
            Input("F", 15L),
            Input("f", 15L),
            Input("G", 16L),
            Input("g", 16L),
            Input("H", 17L),
            Input("h", 17L),
            Input("J", 18L),
            Input("j", 18L),
            Input("K", 19L),
            Input("k", 19L),
            Input("M", 20L),
            Input("m", 20L),
            Input("N", 21L),
            Input("n", 21L),
            Input("P", 22L),
            Input("p", 22L),
            Input("Q", 23L),
            Input("q", 23L),
            Input("R", 24L),
            Input("r", 24L),
            Input("S", 25L),
            Input("s", 25L),
            Input("T", 26L),
            Input("t", 26L),
            Input("V", 27L),
            Input("v", 27L),
            Input("W", 28L),
            Input("w", 28L),
            Input("X", 29L),
            Input("x", 29L),
            Input("Y", 30L),
            Input("y", 30L),
            Input("Z", 31L),
            Input("z", 31L),
            Input("EDNA3444", 0x73_6AA1_9084L),
            Input("ZZZZZZZZZZZZ", 0xFFF_FFFF_FFFF_FFFFL),
            Input(PastTimestampPart, PastTimestamp),
            Input("", 0L),
        )

        for (input in inputs) {
            input.run {
                val result = string.parseCrockford()
                assertEquals(expectedResult, result)
            }
        }
    }

    @Test
    fun test_parseCrockford_fail() {
        assertFailsWith<IllegalArgumentException> {
            "0000000000000".parseCrockford() // 13 chars
        }

        assertFailsWith<IllegalArgumentException> {
            "{".parseCrockford() // illegal char (123)
        }
    }
}
