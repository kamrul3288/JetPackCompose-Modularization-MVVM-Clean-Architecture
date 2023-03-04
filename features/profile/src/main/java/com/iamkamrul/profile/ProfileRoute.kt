package com.iamkamrul.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileRoute(
    viewModel:ProfileViewModel,
    onPopBack:()->Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiAction = remember(viewModel){
        val action:(ProfileUiAction)->Unit = { viewModel.action(it) }
        action
    }

    RepoListRoute(
        uiState = uiState,
        onRefreshProfile = {
            uiAction(ProfileUiAction.FetchProfile)
        },
        onPopBack = onPopBack
    )
}

@Composable
private fun RepoListRoute(
    uiState: ProfileUiState,
    onRefreshProfile:()->Unit,
    onPopBack:()->Unit
){
    ProfileScreen(
        uiState = uiState,
        onRefreshProfile = onRefreshProfile,
        onPopBack = onPopBack
    )
}