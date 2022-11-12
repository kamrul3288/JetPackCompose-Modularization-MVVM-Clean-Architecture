import org.gradle.kotlin.dsl.`kotlin-dsl`
plugins {
    `kotlin-dsl`
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven ("https://jitpack.io")
        maven ("https://oss.jfrog.org/libs-snapshot")
    }
    extra.apply {
        set("kotlinVersion",project.properties["kotlinVersion"])
        set("hiltVersion",System.getProperty("hiltVersion"))
        set("androidGradlePluginVersion",project.properties["androidGradlePluginVersion"])
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    maven ("https://jitpack.io")
    maven ("https://oss.jfrog.org/libs-snapshot")
}


dependencies {
    implementation("com.android.tools.build:gradle:${project.properties["androidGradlePluginVersion"]}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.properties["kotlinVersion"]}")
    implementation("com.google.dagger:hilt-android-gradle-plugin:${System.getProperty("hiltVersion")}")
}