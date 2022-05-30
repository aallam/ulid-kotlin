package com.aallam.ulid.benchmark

import java.util.*

actual fun randomUUID(): String {
    return UUID.randomUUID().toString()
}
