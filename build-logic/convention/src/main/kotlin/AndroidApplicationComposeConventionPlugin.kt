import com.android.build.api.dsl.ApplicationExtension
import com.iamkamrul.configureAndroidCompose
import com.iamkamrul.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply(libs.findPlugin("android-application").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)

            // Configure the Android application extension
            extensions.configure<ApplicationExtension>{
                configureAndroidCompose(this)
                defaultConfig.targetSdk = libs.findVersion("androidSdkVersion").get().toString().toInt()
            }
        }
    }
}