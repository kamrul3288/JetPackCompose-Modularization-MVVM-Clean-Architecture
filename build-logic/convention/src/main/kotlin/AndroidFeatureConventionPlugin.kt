
import com.android.build.gradle.LibraryExtension
import com.iamkamrul.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("iamkamrul.android.library")
                apply("iamkamrul.android.hilt")
                apply("iamkamrul.android.navigation")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                buildFeatures {
                    viewBinding = true
                }
                //configureGradleManagedDevices(this)
            }

            dependencies {
               /* add("implementation", project(":core:di"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":navigation"))
                add("implementation", project(":core:model:entity"))
                add("implementation", project(":library:sharedpref"))
                add("implementation", project(":common"))
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:design-system"))*/

                add("implementation", libs.findLibrary("androidx.appcompat").get())
                add("implementation", libs.findLibrary("androidx.corektx").get())
                add("implementation", libs.findLibrary("androidx.constraintlayout").get())
                add("implementation", libs.findLibrary("androidx.material").get())
                add("implementation", libs.findLibrary("androidx.fragment").get())
                add("implementation", libs.findLibrary("androidx.activity").get())
                add("implementation", libs.findLibrary("androidx.cardview").get())
                add("implementation", libs.findLibrary("androidx.recyclerview").get())

                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.savedstate").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.commonjava8").get())
                add("implementation", libs.findLibrary("image.picasso").get())
                add("implementation", libs.findLibrary("log.timber").get())
                add("implementation", libs.findLibrary("dimension.sdp").get())
                add("implementation", libs.findLibrary("dimension.ssp").get())

                add("implementation", libs.findLibrary("kamrul3288.viewstate").get())
                add("implementation", libs.findLibrary("kamrul3288.customview").get())
                add("implementation", libs.findLibrary("kamrul3288.dateced").get())

                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("gson").get())


                add("testImplementation", kotlin("test"))
                //add("testImplementation", project(":core:testing"))
                add("androidTestImplementation", kotlin("test"))
                //add("androidTestImplementation", project(":core:testing"))


            }
        }
    }
}
