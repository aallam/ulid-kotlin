package ulid

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSerialization {

    @Test
    fun `test ULID serialization and deserialization`() {
        val ulid = ULID.nextULID()

        // Serialize to a byte array
        val serialized = ByteArrayOutputStream().use { bos ->
            ObjectOutputStream(bos).use { oos -> oos.writeObject(ulid) }
            bos.toByteArray()
        }

        // Deserialize from the byte array
        val deserialized = ByteArrayInputStream(serialized).use { bis ->
            ObjectInputStream(bis).use { ois -> ois.readObject() }
        }

        // Check that the objects are equal
        assertEquals(ulid, deserialized)
    }

    @Test
    fun `test ULID with kotlin seralization`() {
        val ulid = ULID.nextULID()

        // Serialize to a byte array
        val serialized = Json.encodeToString<ULID>(ulid)

        // Deserialize from the byte array
        val deserialized = Json.decodeFromString<ULID>(serialized)

        // Check that the objects are equal
        assertEquals(ulid, deserialized)
    }
}
