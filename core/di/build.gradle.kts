plugins {
    alias(libs.plugins.iamkamrul.android.library)
    alias(libs.plugins.iamkamrul.android.hilt)
    alias(libs.plugins.iamkamrul.android.retrofit)
}

android {
    namespace = "com.iamkamrul.di"
}

dependencies {
    api(libs.timber)
    api(libs.bundles.network)
}
