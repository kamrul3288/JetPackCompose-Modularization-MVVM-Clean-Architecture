package com.iamkamrul.modularization.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.iamkamrul.profile.navigateToProfileScreen
import com.iamkamrul.profile.profileScreen
import com.iamkamrul.repolist.repoListScreen
import com.iamkamrul.repolist.repoListScreenRoute

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination:String = repoListScreenRoute
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        repoListScreen(onRepoItemClick = navController::navigateToProfileScreen)
        profileScreen(onBackBtnClick = navController::popBackStackOrIgnore)
    }
}

fun NavController.popBackStackOrIgnore() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}