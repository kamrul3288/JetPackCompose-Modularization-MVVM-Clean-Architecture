plugins {
    alias(libs.plugins.iamkamrul.android.feature.compose)
}

android {
    namespace = "com.iamkamrul.profile"
}

dependencies {
    implementation(libs.coil.compose)
}
