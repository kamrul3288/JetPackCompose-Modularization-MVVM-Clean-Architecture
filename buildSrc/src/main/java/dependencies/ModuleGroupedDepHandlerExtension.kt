package dependencies

import core.*
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.addDiModule(configurationName:String = "implementation"){
    add(configurationName, project(ModulesDep.di))
}

fun DependencyHandler.addDomainModule(){
    add("implementation", project(ModulesDep.domain))
}

fun DependencyHandler.addDataModule(){
    add("implementation", project(ModulesDep.data))
}
fun DependencyHandler.addApiResponseModule(configurationName:String = "implementation"){
    add(configurationName, project(ModulesDep.apiResponse))
}

fun DependencyHandler.addCommonModule(){
    add("implementation", project(ModulesDep.common))
}

fun DependencyHandler.addEntityModule(configurationName:String = "implementation"){
    add(configurationName, project(ModulesDep.entity))
}

fun DependencyHandler.addFeatureModule(){
    featureModule.forEach {
        add("implementation", project(it))
    }
}