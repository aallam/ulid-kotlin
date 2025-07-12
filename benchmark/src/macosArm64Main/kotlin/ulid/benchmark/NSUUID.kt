package ulid.benchmark

import platform.Foundation.NSUUID

actual fun randomUUID(): String {
    return NSUUID().UUIDString
}
