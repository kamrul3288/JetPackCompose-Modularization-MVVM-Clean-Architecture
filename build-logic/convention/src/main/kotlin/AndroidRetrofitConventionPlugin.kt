

import com.iamkamrul.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary("retrofit2.core").get())
                add("implementation", libs.findLibrary("retrofit2.rx3adapter").get())
                add("implementation", libs.findLibrary("retrofit2.gsonconverter").get())
                add("implementation", libs.findLibrary("gson").get())
                add("implementation", libs.findLibrary("okhHttp3.core").get())
                add("implementation", libs.findLibrary("okhHttp3.interceptor").get())
            }
        }
    }
}