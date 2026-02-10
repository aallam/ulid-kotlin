package ulid

import java.util.UUID

/**
 * Convert this [ULID] to a [UUID].
 */
public fun ULID.toUUID(): UUID = UUID(mostSignificantBits, leastSignificantBits)

/**
 * Create a [ULID] from a [UUID].
 */
public fun ULID.Companion.fromUUID(uuid: UUID): ULID = ULID.fromBytes(
    ByteArray(16).also { bytes ->
        val msb = uuid.mostSignificantBits
        val lsb = uuid.leastSignificantBits
        for (i in 0..7) bytes[i] = (msb shr ((7 - i) * 8) and 0xFF).toByte()
        for (i in 8..15) bytes[i] = (lsb shr ((15 - i) * 8) and 0xFF).toByte()
    }
)
