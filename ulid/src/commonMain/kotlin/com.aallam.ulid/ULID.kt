package com.aallam.ulid

import com.aallam.ulid.internal.ULIDFactory
import com.aallam.ulid.internal.ULIDFactory.Companion.Default
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

    public interface Factory {

        /**
         * Generate a ULID String.
         */
        public fun nextULIDString(): String

        /**
         * Generate a ULID String.
         *
         * @param timestamp timestamp epoch in milliseconds
         */
        public fun nextULIDString(timestamp: Long): String

        /**
         * Generate a [ULID] instance.
         */
        public fun nextULID(): ULID

        /**
         * Generate a [ULID].
         *
         * @param timestamp timestamp epoch in milliseconds
         */
        public fun nextULID(timestamp: Long): ULID

        /**
         * Generate a [ULID] from given bytes.
         *
         * @param data byte array, data must be 16 bytes in length.
         */
        public fun fromBytes(data: ByteArray): ULID
    }

    public companion object : Factory by Default {

        public fun Factory(random: Random = Random): Factory = ULIDFactory(random)
    }
}
