package ulid

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSerialization {

    @Test
    fun `test ULID serialization and deserialization`() {
        val ulid = ULID.nextULID()

        // Serialize to a byte array
        val serialized = Json.encodeToString<ULID>(ulid)

        // Deserialize from the byte array
        val deserialized = Json.decodeFromString<ULID>(serialized)

        // Check that the objects are equal
        assertEquals(ulid, deserialized)
    }
}
