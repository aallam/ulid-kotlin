package com.aallam.ulid.utils

import kotlin.random.Random

class MockRandom(private val nextBits: Int) : Random() {
    override fun nextBits(bitCount: Int): Int = nextBits
    override fun nextLong(): Long = nextBits.toLong()
}
