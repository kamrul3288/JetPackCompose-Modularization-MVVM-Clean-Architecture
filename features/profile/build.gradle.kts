@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.iamkamrul.android.feature.compose)
}
android {
    namespace = "com.iamkamrul.profile"
}

dependencies{
    implementation(libs.image.coil.compose)
}