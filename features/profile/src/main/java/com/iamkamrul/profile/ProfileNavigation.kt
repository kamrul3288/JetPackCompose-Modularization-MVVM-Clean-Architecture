package com.iamkamrul.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val profileScreenRoute = "profileScreenRoute"

fun NavController.navigateToProfileScreen(){
    navigate(profileScreenRoute)
}

fun NavGraphBuilder.profileScreen(
    onBackBtnClick:()->Unit
){
    composable(route = profileScreenRoute){
        ProfileScreenRoute(
            onBackBtnClick = onBackBtnClick
        )
    }
}