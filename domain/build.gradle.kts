import dependencies.addEntityModule

plugins {
   plugins.`android-core-library`
}

android {
    namespace = "com.iamkamrul.domain"
}

dependencies {
    addEntityModule(configurationName = "api")
}