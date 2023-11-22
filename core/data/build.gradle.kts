@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iamkamrul.android.library)
    alias(libs.plugins.iamkamrul.android.hilt)
}
android {
    namespace = "com.iamkamrul.data"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.di)
    api(projects.model.apiresponse)
}