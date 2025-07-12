import kotlinx.benchmark.gradle.benchmark
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    kotlin("multiplatform")
    kotlin("plugin.allopen")
    id("org.jetbrains.kotlinx.benchmark")
    id("com.diffplug.spotless")
    alias(libs.plugins.kotlin.serialization)
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

kotlin {
    jvm()
    js(IR) {
        nodejs()
    }
    if (HostManager.hostIsMac) {
        macosArm64()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":ulid"))
                implementation(libs.kotlinx.benchmark)
                implementation(libs.kotlinx.serialization.core)
            }
        }
    }
}

benchmark {
    configurations {
        named("main") {
            iterations = 5
            warmups = 5
        }
    }
    targets {
        register("jvm")
        register("js")
        if (HostManager.hostIsMac) {
            register("macosArm64")
        }
    }
}
