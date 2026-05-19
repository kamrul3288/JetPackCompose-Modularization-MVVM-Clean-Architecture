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
    implementation(libs.timber)
    implementation(libs.kotlinx.coroutines.android)
}
