package com.iamkamrul.modularization.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iamkamrul.common.utils.NavRoute
import com.iamkamrul.repolist.RepoListScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoute.repoListScreen ){

        composable(NavRoute.repoListScreen){
            RepoListScreen()
        }

        composable(NavRoute.profileScreen){

        }
    }
}