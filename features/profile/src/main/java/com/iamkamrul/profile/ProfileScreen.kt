package com.iamkamrul.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iamkamrul.designsystem.component.ScaffoldTopAppbar
import com.iamkamrul.domain.entity.ProfileEntity
import com.iamkamrul.ui.components.NetworkErrorMessage
import com.iamkamrul.ui.error.asUiText

// Smart composable: owns ViewModel, maps state → props, actions → callbacks
@Composable
internal fun ProfileScreenRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    ProfileScreen(
        uiState = uiState,
        onRetry = { viewModel.handleAction(ProfileUiAction.FetchProfile) },
        onBackClick = onBackClick
    )
}

// Dumb composable: pure UI, no ViewModel knowledge, no Action types
@Composable
private fun ProfileScreen(
    uiState: ProfileUiState,
    onRetry: () -> Unit,
    onBackClick: () -> Unit
) {
    ScaffoldTopAppbar(
        title = "Profile",
        onNavigationIconClick = onBackClick
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                ProfileUiState.Loading -> CircularProgressIndicator()
                is ProfileUiState.Success -> ProfileContent(profile = uiState.data)
                is ProfileUiState.Error -> NetworkErrorMessage(
                    message = uiState.error.asUiText(),
                    onClickRefresh = onRetry
                )
            }
        }
    }
}

@Composable
private fun ProfileContent(
    profile: ProfileEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = profile.userAvatar,
            contentDescription = "Avatar of ${profile.userName}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = profile.userFullName,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "@${profile.userName}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (profile.about.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = profile.about,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(24.dp))

        ProfileStats(
            repoCount = profile.repoCount,
            followerCount = profile.followerCount,
            followingCount = profile.followingCount
        )
    }
}

@Composable
private fun ProfileStats(
    repoCount: Int,
    followerCount: Int,
    followingCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(count = repoCount, label = "Repositories")
        StatItem(count = followerCount, label = "Followers")
        StatItem(count = followingCount, label = "Following")
    }
}

@Composable
private fun StatItem(
    count: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
