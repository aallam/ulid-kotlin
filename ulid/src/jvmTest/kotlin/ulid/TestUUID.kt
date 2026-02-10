package ulid

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TestUUID {

    @Test
    fun `test ULID to UUID round-trip`() {
        val ulid = ULID.nextULID()
        val uuid = ulid.toUUID()
        val roundTripped = ULID.fromUUID(uuid)
        assertEquals(ulid, roundTripped)
    }

    @Test
    fun `test UUID to ULID round-trip`() {
        val uuid = UUID.randomUUID()
        val ulid = ULID.fromUUID(uuid)
        val roundTripped = ulid.toUUID()
        assertEquals(uuid, roundTripped)
    }

    @Test
    fun `test toUUID preserves bits`() {
        val ulid = ULID.nextULID()
        val uuid = ulid.toUUID()
        assertEquals(ulid.mostSignificantBits, uuid.mostSignificantBits)
        assertEquals(ulid.leastSignificantBits, uuid.leastSignificantBits)
    }

    @Test
    fun `test fromUUID preserves bits`() {
        val uuid = UUID.randomUUID()
        val ulid = ULID.fromUUID(uuid)
        assertEquals(uuid.mostSignificantBits, ulid.mostSignificantBits)
        assertEquals(uuid.leastSignificantBits, ulid.leastSignificantBits)
    }
}
