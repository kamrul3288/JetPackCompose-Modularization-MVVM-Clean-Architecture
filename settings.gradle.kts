@file:Suppress("UnstableApiUsage", "DEPRECATION")

rootProject.buildFileName = "build.gradle.kts.kts"
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        google()
        maven("https://jitpack.io")
        maven("https://oss.jfrog.org/libs-snapshot")

    }

    gradle.projectsLoaded {
        plugins{
            plugins {
                id ("com.android.application") version(extra.properties["androidGradlePluginVersion"].toString())
                id ("com.android.library") version(extra.properties["androidGradlePluginVersion"].toString())
                id ("org.jetbrains.kotlin.android") version(extra.properties["kotlinVersion"].toString())
                id ("com.google.dagger.hilt.android") version(extra.properties["hiltVersion"].toString())
            }
        }
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        google()
        maven("https://jitpack.io")
        maven("https://oss.jfrog.org/libs-snapshot")
    }
}
rootProject.name = "JetPackComposeModularization"
include (":app")
include(":di")
include(":model:entity")
include(":model:apiresponse")
include(":data")
include(":domain")
include(":common")

include(":features:repolist")
include(":features:profile")

