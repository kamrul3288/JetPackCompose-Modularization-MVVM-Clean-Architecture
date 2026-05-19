package com.iamkamrul.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable


@Serializable
data object ProfileRoute


fun NavGraphBuilder.profileScreen(
    onBackClick: () -> Unit
) {
    composable<ProfileRoute> {
        ProfileScreenRoute(
            onBackClick = onBackClick
        )
    }
}