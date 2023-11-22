package com.iamkamrul.repolist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val repoListScreenRoute = "repoListScreenRoute"

fun NavGraphBuilder.repoListScreen(
    onRepoItemClick:()->Unit
){
    composable(route = repoListScreenRoute){
        RepoListRoute(
            onRepoItemClick = onRepoItemClick
        )
    }
}