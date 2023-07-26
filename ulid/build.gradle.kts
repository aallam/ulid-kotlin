import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
    id("binary-compatibility-validator")
    id("com.diffplug.spotless")
    id("org.jetbrains.dokka")
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    explicitApi()
    jvm()
    js {
        compilations.all {
            kotlinOptions {
                moduleKind = "umd"
                sourceMap = true
                metaInfo = true
                main = "noCall"
                sourceMapEmbedSources = "always"
            }
        }
        nodejs()
        browser()
    }

    if (HostManager.hostIsMac) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
        macosX64()
        macosArm64()
        tvosX64()
        tvosArm64()
        tvosSimulatorArm64()
        watchosArm32()
        watchosArm64()
        watchosX86()
        watchosX64()
        watchosSimulatorArm64()
    }

    if (HostManager.hostIsMingw || HostManager.hostIsMac) {
        mingwX64 {
            binaries.findTest(DEBUG)?.linkerOpts = mutableListOf("-Wl,--subsystem,windows")
        }
    }

    if (HostManager.hostIsLinux || HostManager.hostIsMac) {
        linuxX64()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations.common)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test.junit)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(libs.kotlin.test.js)
            }
        }
    }
}
