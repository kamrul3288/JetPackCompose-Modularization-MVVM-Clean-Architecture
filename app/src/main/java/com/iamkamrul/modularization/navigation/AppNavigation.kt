package com.iamkamrul.modularization.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.iamkamrul.profile.ProfileRoute
import com.iamkamrul.profile.profileScreen
import com.iamkamrul.repolist.RepoListRoute
import com.iamkamrul.repolist.repoListScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = RepoListRoute,
        modifier = modifier
    ) {
        repoListScreen(
            onRepoItemClick = { navController.navigate(ProfileRoute) }
        )
        profileScreen(onBackClick = navController::popBackStackOrIgnore)
    }
}

fun NavController.popBackStackOrIgnore() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}