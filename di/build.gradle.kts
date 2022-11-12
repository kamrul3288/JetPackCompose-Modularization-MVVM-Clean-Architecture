import dependencies.addTimberDependencies

plugins {
    plugins.`android-core-library`
}
android {
    namespace = "com.iamkamrul.di"
}
dependencies {
    addTimberDependencies(configurationName = "api")
}
