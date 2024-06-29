import kotlinx.benchmark.gradle.JvmBenchmarkTarget
import kotlinx.benchmark.gradle.benchmark
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    kotlin("multiplatform")
    kotlin("plugin.allopen")
    id("org.jetbrains.kotlinx.benchmark")
    id("com.diffplug.spotless")
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

kotlin {
    jvm()
    js {
        nodejs()
    }

    if (HostManager.hostIsMac) {
        if (HostManager.hostArch() == "aarch64") macosArm64() else macosX64()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":ulid"))
                implementation(libs.kotlinx.benchmark)
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
        register("macosX64")
    }
}
