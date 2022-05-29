import kotlinx.benchmark.gradle.JvmBenchmarkTarget
import kotlinx.benchmark.gradle.benchmark
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

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
    js { nodejs() }
    macosX64()

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
            iterationTime = 5
            iterationTimeUnit = "sec"

        }
    }
    targets {
        // This one matches compilation base name, e.g. 'jvm', 'jvmTest', etc
        register("jvm") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
        register("js")
        register("macosX64")
        register("linuxX64")
        register("mingwX64")
    }
}
