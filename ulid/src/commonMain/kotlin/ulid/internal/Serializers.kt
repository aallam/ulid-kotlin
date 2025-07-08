package ulid.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ulid.ULID

public object ULIDAsStringSerializer : KSerializer<ULID> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ulid.ULID", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: ULID) {
    val string = value.toString()
    encoder.encodeString(string)
  }

  override fun deserialize(decoder: Decoder): ULID {
    val string = decoder.decodeString()
    return ULID.parseULID(string)
  }
}
