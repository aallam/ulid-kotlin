import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
    id("binary-compatibility-validator")
    id("com.diffplug.spotless")
}

kotlin {
    explicitApi()
    jvm()
    js(BOTH) {
        compilations.all {
            kotlinOptions {
                moduleKind = "umd"
                sourceMap = true
                metaInfo = true
            }
        }
        browser()
        nodejs()
    }

    if (HostManager.hostIsMac) {
        macosX64()
        macosArm64()
        iosX64()
        iosArm64()
        iosSimulatorArm64()
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
