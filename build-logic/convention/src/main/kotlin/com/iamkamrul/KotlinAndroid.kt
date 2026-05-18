

package com.iamkamrul

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Method: configureKotlinAndroid
 *
 * Goal:
 * This method standardizes Kotlin and Java-related configurations for Android modules.
 * It ensures that all modules are compatible with Java 17 and use consistent Kotlin compiler settings.
 *
 * Why is it needed?
 * - **Modern Java Support**: Enables Java 17 features, ensuring the project is future-proof and uses the latest language features.
 * - **Kotlin Consistency**: Applies shared Kotlin settings across all Android modules.
 * - **Backward Compatibility**: Supports older Android versions (minSdk 21) by enabling core library desugaring.
 * - **Maintainability**: Centralizes Kotlin-related configurations, making them easier to manage.
 *
 * Parameters:
 * @param commonExtension - The Android DSL extension to configure the module.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("androidSdkVersion").get().toString().toInt()

        defaultConfig.apply {
            minSdk = libs.findVersion("minSdkVersion").get().toString().toInt()
        }

        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin<KotlinAndroidProjectExtension>()

    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

/// Configure base Kotlin options for JVM (non-Android)
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    configureKotlin<KotlinJvmProjectExtension>()
}

/**
 * Shared Kotlin Compiler Configuration
 *
 * Goal:
 * This utility function applies shared Kotlin compiler options to all modules (Android or non-Android).
 * It enforces consistent settings like `jvmTarget`, experimental features, and warning handling.
 *
 * Why is it needed?
 * - **Code Quality**: Treats warnings as errors (if enabled) to enforce stricter standards.
 * - **Experimental APIs**: Allows opt-in to experimental Kotlin features (e.g., coroutines).
 * - **Future-Proofing**: Ensures compatibility with upcoming changes to Kotlin and Java.
 *
 * Parameters:
 * @param T - A Kotlin top-level extension type (e.g., `KotlinAndroidProjectExtension`).
 */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() = configure<T> {
    // Treat warnings as errors if enabled via gradle.properties
    val warningsAsErrors: String? by project
    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> throw IllegalArgumentException("Unsupported project extension $this ${T::class}")
    }.apply {
        jvmTarget = JvmTarget.JVM_17
        //Reads a property named warningsAsErrors from the Gradle project’s gradle.properties file
        //If the property is set (e.g., warningsAsErrors=true), its value is used; otherwise, it defaults to null.
        //This allows developers to enable or disable treating warnings as errors based on a configurable setting in gradle.properties.
        allWarningsAsErrors = warningsAsErrors.toBoolean()

        /// This flag enables the use of experimental coroutines APIs (like Flow, Channel, or StateFlow) that are still in an experimental phase.
        /// Kotlin’s experimental APIs are not stable, and they require explicit opt-in to acknowledge potential future-breaking changes.
        ///  without it You cannot use experimental coroutines APIs in your code (compilation will fail if they’re used).
        freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")

        /// Kotlin is deprecating the old behavior where the copy method’s visibility could differ from the constructor’s.
        /// This flag ensures that your project follows the new behavior now, avoiding potential issues when the change becomes mandatory in future Kotlin versions.
        /// Without it our code will still work for now, but you may face issues in the future as the Kotlin team enforces this behavior (likely in Kotlin 2.2 or 2.3).
        freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
    }
}