package com.iamkamrul.repolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ForkRight
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iamkamrul.designsystem.component.ScaffoldTopAppbar
import com.iamkamrul.designsystem.theme.color
import com.iamkamrul.domain.entity.RepoItemEntity
import com.iamkamrul.ui.components.NetworkErrorMessage
import com.iamkamrul.ui.error.asUiText

// Smart composable: owns ViewModel, maps state → props, actions → callbacks
@Composable
internal fun RepoListScreenRoute(
    viewModel: RepoListViewModel = hiltViewModel(),
    onRepoItemClick: () -> Unit
) {
    val uiState by viewModel.repoListUiState.collectAsStateWithLifecycle()
    RepoListScreen(
        uiState = uiState,
        onRepoItemClick = onRepoItemClick,
        onRetry = { viewModel.handleAction(RepoListUiAction.FetchRepoList) }
    )
}

// Dumb composable: pure UI, no ViewModel knowledge, no Action types
@Composable
private fun RepoListScreen(
    uiState: RepoListUiState,
    onRepoItemClick: () -> Unit,
    onRetry: () -> Unit
) {
    ScaffoldTopAppbar(
        title = "Repositories",
        containerColor = MaterialTheme.color.secondaryBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                RepoListUiState.Loading -> CircularProgressIndicator()
                is RepoListUiState.HasRepoList -> RepoList(
                    repoList = uiState.repoList,
                    onItemClick = onRepoItemClick
                )

                RepoListUiState.RepoListEmpty -> EmptyRepoListContent()
                is RepoListUiState.Error -> NetworkErrorMessage(
                    message = uiState.error.asUiText(),
                    onClickRefresh = onRetry
                )
            }
        }
    }
}

@Composable
private fun RepoList(
    repoList: List<RepoItemEntity>,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = repoList, key = { it.repoFullName }) { repoItem ->
            RepoListItem(
                repoItem = repoItem,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun EmptyRepoListContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No repositories found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RepoListItem(
    repoItem: RepoItemEntity,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onItemClick,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.color.white
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RepoItemHeader(
                avatarUrl = repoItem.userAvatarUrl,
                repoName = repoItem.repoName,
                userName = repoItem.userName
            )
            if (repoItem.repoDescription.isNotBlank()) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = repoItem.repoDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            RepoItemStats(
                language = repoItem.language,
                stargazersCount = repoItem.stargazersCount,
                forksCount = repoItem.forksCount
            )
        }
    }
}

@Composable
private fun RepoItemHeader(
    avatarUrl: String,
    repoName: String,
    userName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "Avatar of $userName",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = repoName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RepoItemStats(
    language: String,
    stargazersCount: Int,
    forksCount: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        RepoStatChip(
            modifier = Modifier.weight(1f),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Language,
                    contentDescription = "Language",
                    modifier = Modifier.size(14.dp)
                )
            },
            label = language.ifBlank { "N/A" }
        )
        RepoStatChip(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.StarBorder,
                    contentDescription = "Stars",
                    modifier = Modifier.size(14.dp)
                )
            },
            label = "$stargazersCount Stars"
        )
        RepoStatChip(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ForkRight,
                    contentDescription = "Forks",
                    modifier = Modifier.size(14.dp)
                )
            },
            label = "$forksCount Forks"
        )
    }
}

@Composable
private fun RepoStatChip(
    icon: @Composable () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
