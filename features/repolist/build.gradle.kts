plugins {
    alias(libs.plugins.iamkamrul.android.feature.compose)
}

android {
    namespace = "com.iamkamrul.repolist"
}

dependencies {
    implementation(libs.coil.compose)
}
