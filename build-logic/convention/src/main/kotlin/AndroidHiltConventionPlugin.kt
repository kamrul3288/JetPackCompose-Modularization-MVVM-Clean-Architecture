import com.iamkamrul.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("google-ksp").get().get().pluginId)
                apply(libs.findPlugin("google-hilt-android").get().get().pluginId)
            }

            dependencies {
                add("implementation", libs.findLibrary("hilt-android").get())
                add("ksp", libs.findLibrary("hilt-compiler").get())
                add("kspAndroidTest", libs.findLibrary("hilt-compiler").get())
                add("kspTest", libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}
