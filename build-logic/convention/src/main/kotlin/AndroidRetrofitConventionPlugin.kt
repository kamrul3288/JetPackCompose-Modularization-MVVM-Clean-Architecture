import com.iamkamrul.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary("retrofit2-core").get())
                add("implementation", libs.findLibrary("retrofit2-adapter-rxjava3").get())
                add("implementation", libs.findLibrary("retrofit2-converter-gson").get())
                add("implementation", libs.findLibrary("gson").get())
                add("implementation", libs.findLibrary("okhttp3").get())
                add("implementation", libs.findLibrary("okhttp3-logging-interceptor").get())
            }
        }
    }
}
