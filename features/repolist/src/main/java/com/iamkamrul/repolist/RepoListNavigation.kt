package com.iamkamrul.repolist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RepoListRoute

fun NavGraphBuilder.repoListScreen(
    onRepoItemClick: () -> Unit
) {
    composable<RepoListRoute> {
        RepoListScreenRoute(
            onRepoItemClick = onRepoItemClick
        )
    }
}