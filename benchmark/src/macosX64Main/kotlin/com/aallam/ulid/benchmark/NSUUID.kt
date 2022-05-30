package com.aallam.ulid.benchmark

import platform.Foundation.NSUUID

val uuid = NSUUID()

actual fun randomUUID(): String {
    return uuid.UUIDString
}
