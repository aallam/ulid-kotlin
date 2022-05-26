import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    id("binary-compatibility-validator")
    id("com.diffplug.spotless")
}

kotlin {
    explicitApi()
    jvm()
    js { nodejs() }
    native()

    sourceSets {
        val commonMain by getting
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

fun KotlinMultiplatformExtension.native() {
    val targets = mutableListOf<KotlinNativeTarget>().apply {
        add(linuxX64())
        add(macosX64())
        add(macosArm64())
        add(mingwX64())
    }
    sourceSets.apply {
        val nativeMain by creating { dependsOn(getByName("commonMain")) }
        val nativeTest by creating { dependsOn(getByName("commonTest")) }
        targets.forEach { target ->
            getByName("${target.name}Main").dependsOn(nativeMain)
            getByName("${target.name}Test").dependsOn(nativeTest)
        }
    }
}
