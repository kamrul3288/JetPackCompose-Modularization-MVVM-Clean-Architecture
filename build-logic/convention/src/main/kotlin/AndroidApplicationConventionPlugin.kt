import com.android.build.api.dsl.ApplicationExtension
import com.iamkamrul.configureKotlinAndroid
import com.iamkamrul.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.findPlugin("android-application").get().get().pluginId)

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("android-compile-sdk").get().toString().toInt()
            }
        }
    }
}
