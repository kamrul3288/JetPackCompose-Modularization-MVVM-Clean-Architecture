plugins {
    id ("com.android.application") apply(false)
    id ("com.android.library") apply(false)
    id ("org.jetbrains.kotlin.android") apply(false)
    id ("com.google.dagger.hilt.android") apply(false)
    id("org.jetbrains.kotlin.jvm") apply (false)
}

tasks.create<Delete>("clean") {
    delete  = setOf(
        rootProject.buildDir
    )
}
gradle.projectsLoaded{
    configurations.all {
        configurations.all {
            resolutionStrategy {
                eachDependency {
                    if ((requested.group == "org.jetbrains.kotlin") && (!requested.name.startsWith("kotlin-gradle"))) {
                        useVersion(extra.properties["kotlinVersion"].toString())
                    }
                }
                force(
                    "org.jetbrains.kotlin:kotlin-stdlib:${extra.properties["kotlinVersion"].toString()}",
                    "org.jetbrains.kotlin:kotlin-stdlib-common:${extra.properties["kotlinVersion"].toString()}",
                    "org.jetbrains.kotlin:kotlin-reflect:${extra.properties["kotlinVersion"].toString()}",
                )
            }
        }
    }
}