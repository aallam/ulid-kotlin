package com.aallam.ulid.utils

import kotlin.random.Random

class MockRandom(private val nextBits: Int = 0) : Random() {
    override fun nextBits(bitCount: Int): Int = nextBits
}
