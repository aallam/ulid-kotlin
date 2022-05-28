package com.aallam.ulid

import com.aallam.ulid.internal.ULIDFactory
import com.aallam.ulid.internal.ULIDFactory.Companion.Default
import com.aallam.ulid.internal.ULIDMonotonic
import com.aallam.ulid.internal.ULIDMonotonic.Companion.DefaultMonotonic
import com.aallam.ulid.internal.currentTimeMillis
import kotlin.random.Random

/**
 * Universally Unique Lexicographically Sortable Identifier.
 *
 * [Specification](https://github.com/ulid/spec#specification)
 */
public interface ULID : Comparable<ULID> {

    /**
     * The most significant 64 bits of this ULID.
     */
    public val mostSignificantBits: Long

    /**
     * The least significant 64 bits of this ULID.
     */
    public val leastSignificantBits: Long

    /**
     * Get timestamp.
     */
    public val timestamp: Long

    /**
     * Generate the [ByteArray] for this [ULID].
     */
    public fun toBytes(): ByteArray

    public fun increment(): ULID

    /**
     * [ULID] factory.
     */
    public interface Factory {

        /**
         * Generate a ULID String.
         *
         * @param timestamp timestamp epoch in milliseconds
         */
        public fun nextULIDString(timestamp: Long = currentTimeMillis()): String


        /**
         * Generate a [ULID].
         *
         * @param timestamp timestamp epoch in milliseconds
         */
        public fun nextULID(timestamp: Long = currentTimeMillis()): ULID

        /**
         * Generate a [ULID] from given bytes.
         *
         * @param data byte array, data must be 16 bytes in length.
         */
        public fun fromBytes(data: ByteArray): ULID

        /**
         * Generate a [ULID] from given [ulidString].
         */
        public fun parseULID(ulidString: String): ULID
    }

    /**
     * Monotonic [ULID] factory.
     *
     * [Specification](https://github.com/ulid/spec#monotonicity)
     */
    public interface Monotonic {

        public fun nextULID(previousULID: ULID, timestamp: Long = currentTimeMillis()): ULID

        public fun nextULIDStrict(previousULID: ULID, timestamp: Long = currentTimeMillis()): ULID?

        public companion object: Monotonic by DefaultMonotonic

    }

    public companion object : Factory by Default {

        /**
         * Creates an instance of [ULID.Factory].
         *
         * @param random random number generator
         */
        public fun Factory(random: Random = Random): Factory = ULIDFactory(random)

        /**
         * Creates an instance of [ULID.Monotonic].
         *
         * @param factory ULID factory instance
         */
        public fun Monotonic(factory: Factory = ULID): Monotonic = ULIDMonotonic(factory)
    }
}
