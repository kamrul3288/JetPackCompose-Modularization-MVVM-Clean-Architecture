

import com.android.build.api.dsl.ApplicationExtension
import com.iamkamrul.configureKotlinAndroid
import com.iamkamrul.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            // Apply essential plugins for Android and Kotlin
            pluginManager.apply(libs.findPlugin("android-application").get().get().pluginId)


            // Configure the Android application extension
            extensions.configure<ApplicationExtension>{
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("androidSdkVersion").get().toString().toInt()
            }
        }
    }

}