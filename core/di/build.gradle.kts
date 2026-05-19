plugins {
    alias(libs.plugins.iamkamrul.android.library)
    alias(libs.plugins.iamkamrul.android.hilt)
}

android {
    namespace = "com.iamkamrul.di"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
