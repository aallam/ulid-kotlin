import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplaform) apply false
    alias(libs.plugins.kotlin.allopen) apply false
    alias(libs.plugins.kotlinx.binary.validator) apply false
    alias(libs.plugins.kotlinx.kover) apply false
    alias(libs.plugins.kotlinx.benchmark) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.dokka) apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<SpotlessExtension> {
        kotlin {
            ktlint()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    tasks.withType<Test> {
        testLogging {
            events(STARTED, PASSED, SKIPPED, FAILED)
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = false
        }
    }
}
