package com.aallam.ulid

import com.aallam.ulid.internal.RandomULID
import com.aallam.ulid.internal.RandomULID.Companion.Default
import com.aallam.ulid.internal.ULIDValue
import kotlin.random.Random

/**
 * Universally Unique Lexicographically Sortable Identifier.
 *
 * [Specification](https://github.com/ulid/spec#specification)
 */
public interface ULID {

    /**
     * Generate a ULID String.
     */
    public fun nextULID(): String

    /**
     * Generate a ULID String.
     *
     * @param timestamp timestamp epoch in milliseconds
     */
    public fun nextULID(timestamp: Long): String

    /**
     * Generate a ULID [Value] instance.
     */
    public fun nextValue(): Value

    /**
     * Generate a ULID [Value] instance.
     *
     * @param timestamp timestamp epoch in milliseconds
     */
    public fun nextValue(timestamp: Long): Value

    /**
     * Generate a [ULID.Value] from given bytes.
     *
     * @param data byte array, data must be 16 bytes in length.
     */
    public fun fromBytes(data: ByteArray): Value

    /**
     * ULID Value
     */
    public interface Value : Comparable<Value> {

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
         * Generate the [ByteArray] for this [ULID.Value].
         */
        public fun toBytes(): ByteArray

        public companion object
    }

    public companion object : ULID by Default {

        /**
         * Create [Value] instance.
         *
         * @param mostSignificantBits most significant bits
         * @param leastSignificantBits least significant bits
         */
        public fun Value(mostSignificantBits: Long, leastSignificantBits: Long): Value =
            ULIDValue(mostSignificantBits, leastSignificantBits)
    }
}

/**
 * Creates [ULID] instance.
 *
 * @param random random number generator
 */
public fun ULID(random: Random = Random): ULID = RandomULID(random)
