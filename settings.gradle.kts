pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
    }
}

rootProject.name = "JetPackComposeModularization"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include (":app")
include(":di")
include(":domain")

include(":model:entity")
include(":model:apiresponse")
include(":data")
include(":common")
include(":features:repolist")
include(":features:profile")

