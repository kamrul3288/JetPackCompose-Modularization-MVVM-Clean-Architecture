package com.iamkamrul

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {
        buildFeatures.apply {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        }

    }
    /**
     * This configuration enables optional features for the Jetpack Compose Compiler,
     * such as metrics collection, report generation, and custom stability settings.
     *
     * Purpose:
     * - Metrics Collection: Gathers performance data (e.g., build times, memory usage) to help analyze and optimize the Compose compiler.
     * - Report Generation: Produces detailed reports about the Compose compiler's behavior for debugging and performance insights.
     * - Stability Configurations: Allows customization of Compose compiler behavior using a dedicated configuration file.
     *
     * How It Works:
     * - The configuration checks for Gradle properties (`enableComposeCompilerMetrics`, `enableComposeCompilerReports`) in `gradle.properties`.
     * - If enabled, it sets up directories for storing metrics and reports relative to the root project directory.
     * - A custom configuration file (`compose_compiler_config.conf`) is used to define stability-related options.
     *
     * Use Case:
     * - Suitable for projects using Jetpack Compose, especially if you need to debug or optimize the Compose compiler.
     * - Metrics and reports are useful for identifying bottlenecks or inefficiencies during compilation.
     *
     * Impact:
     * - If metrics and reports are not enabled, the configuration is skipped, and no additional overhead is incurred.
     */
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        /**
         * Helper Function: onlyIfTrue
         * Checks if a Gradle property is set to `true` in `gradle.properties` and enables the feature accordingly.
         * Example:
         *   In gradle.properties:
         *     enableComposeCompilerMetrics=true
         */
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }

        /**
         * Helper Function: relativeToRootProject
         * Dynamically resolves a directory path relative to the root project directory.
         * Example:
         *   If the root project is `/project` and the argument is "compose-metrics", the resulting path will be `/project/build/compose-metrics`.
         */
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        /**
         * Enable Compose Compiler Metrics
         * - Checks the `enableComposeCompilerMetrics` property in `gradle.properties`.
         * - If enabled, sets the directory for storing Compose compiler metrics (e.g., `build/compose-metrics`).
         */
        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        /**
         * Enable Compose Compiler Reports
         * - Checks the `enableComposeCompilerReports` property in `gradle.properties`.
         * - If enabled, sets the directory for storing Compose compiler reports (e.g., `build/compose-reports`).
         */
        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        /**
         * Stability Configuration File
         * - Specifies a custom configuration file (`compose_compiler_config.conf`) for the Compose compiler.
         * - This file can include experimental settings or stability-related options for debugging.
         * Example File Content:
         *   debug=true
         *   timeout=120
         */
        stabilityConfigurationFiles.add(rootProject.layout.projectDirectory.file("compose_compiler_config.conf"))
    }
}