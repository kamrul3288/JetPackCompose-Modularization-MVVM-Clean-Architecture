package com.iamkamrul.modularization.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iamkamrul.common.utils.NavRoute
import com.iamkamrul.profile.ProfileRoute
import com.iamkamrul.profile.ProfileViewModel
import com.iamkamrul.repolist.RepoListRoute
import com.iamkamrul.repolist.RepoListViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController, startDestination = NavRoute.repoListScreen ){

        composable(NavRoute.repoListScreen){
            val viewModel:RepoListViewModel = hiltViewModel()
            RepoListRoute(viewModel = viewModel){
                navController.navigate(NavRoute.profileScreen)
            }
        }

        composable(NavRoute.profileScreen){
            val viewModel:ProfileViewModel = hiltViewModel()
            ProfileRoute(
                viewModel = viewModel,
                onPopBack = {navController.popBackStack()}
            )
        }
    }
}