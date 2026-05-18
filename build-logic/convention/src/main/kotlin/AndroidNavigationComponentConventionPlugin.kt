import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidNavigationComponentConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("androidx.navigation.safeargs.kotlin")
        }
    }
}
