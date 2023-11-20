@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iamkamrul.android.library)
    alias(libs.plugins.iamkamrul.android.hilt)
    alias(libs.plugins.iamkamrul.android.retrofit)
}
android {
    namespace = "com.iamkamrul.data"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.di)
    implementation(libs.log.timber)
    api(projects.model.apiresponse)
}