plugins {
    alias(libs.plugins.iamkamrul.android.library)
    alias(libs.plugins.iamkamrul.android.hilt)
}

android {
    namespace = "com.iamkamrul.domain"
}

dependencies {
    api(projects.model.entity)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
}
