import org.jetbrains.kotlin.gradle.dsl.JsMainFunctionExecutionMode
import org.jetbrains.kotlin.gradle.dsl.JsModuleKind
import org.jetbrains.kotlin.gradle.dsl.JsSourceMapEmbedMode
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
    id("binary-compatibility-validator")
    id("com.diffplug.spotless")
    id("org.jetbrains.dokka")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    applyDefaultHierarchyTemplate()

    explicitApi()
    jvm()
    js(IR) {
        compilerOptions {
            moduleKind = JsModuleKind.MODULE_UMD
            sourceMap = true
            sourceMapEmbedSources = JsSourceMapEmbedMode.SOURCE_MAP_SOURCE_CONTENT_ALWAYS
            main = JsMainFunctionExecutionMode.NO_CALL
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
                implementation(libs.kotlinx.serialization.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations.common)
                implementation(libs.kotlinx.serialization.json)
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
