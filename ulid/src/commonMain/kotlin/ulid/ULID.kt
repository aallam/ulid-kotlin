package ulid

import kotlinx.serialization.Serializable
import ulid.internal.ULIDAsStringSerializer
import ulid.internal.ULIDFactory
import ulid.internal.ULIDFactory.Companion.Default
import ulid.internal.ULIDMonotonic
import ulid.internal.ULIDMonotonic.Companion.DefaultMonotonic
import ulid.internal.currentTimeMillis
import kotlin.random.Random

/**
 * Universally Unique Lexicographically Sortable Identifier.
 *
 * [Specification](https://github.com/ulid/spec#specification)
 */
@Serializable(with = ULIDAsStringSerializer::class)
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

    /**
     * Increment the random part of this [ULID] by 1.
     *
     * If the 80-bit random part overflows, the random part is reset to zero while the timestamp remains unchanged.
     */
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
        public fun randomULID(timestamp: Long = currentTimeMillis()): String


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

        /**
         * Get the next monotonic [ULID]. If an overflow happened while incrementing the random part of the given
         * previous [ULID] value then the returned value will have a zero random part.
         *
         * @param previous the previous ULID value
         * @param timestamp the timestamp of the next ULID value
         */
        public fun nextULID(previous: ULID, timestamp: Long = currentTimeMillis()): ULID

        /**
         * Returns the next monotonic [ULID] or `null` if an overflow happened while incrementing the random part of
         * the given previous ULID value.
         *
         * @param previous the previous ULID value.
         * @param timestamp the timestamp of the next ULID value.
         * @return the next monotonic value or empty if an overflow happened.
         */
        public fun nextULIDStrict(previous: ULID, timestamp: Long = currentTimeMillis()): ULID?

        public companion object : Monotonic by DefaultMonotonic

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
