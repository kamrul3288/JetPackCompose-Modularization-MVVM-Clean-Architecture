@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.iamkamrul.android.library)
    alias(libs.plugins.iamkamrul.android.hilt)
}

android {
    namespace = "com.iamkamrul.domain"
}

dependencies {
    api(projects.model.entity)
    implementation(libs.androidx.corektx)
}