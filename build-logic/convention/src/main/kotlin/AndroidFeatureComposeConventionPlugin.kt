import com.android.build.api.dsl.LibraryExtension
import com.iamkamrul.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("iamkamrul.android.library")
                apply("iamkamrul.android.library.compose")
                apply("iamkamrul.android.hilt")
                apply(libs.findPlugin("kotlinx-serialization").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                add("implementation", project(":core:di"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":common"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:ui"))

                add("implementation", libs.findLibrary("androidx-compose-hilt-navigation").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-viewmodel-compose").get()
                )
                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findLibrary("kotlinx-coroutines-android").get())

                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-ext-junit").get())
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-test-espresso-core").get()
                )
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
            }
        }
    }
}
