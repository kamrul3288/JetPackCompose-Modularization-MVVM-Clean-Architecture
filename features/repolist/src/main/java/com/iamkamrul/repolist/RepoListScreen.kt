package com.iamkamrul.repolist
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.iamkamrul.designsystem.component.ScaffoldTopAppbar
import com.iamkamrul.designsystem.theme.color
import com.iamkamrul.entity.RepoItemEntity
import com.iamkamrul.ui.component.NetworkErrorMessage

@Composable
internal fun RepoListRoute(
    viewModel:RepoListViewModel = hiltViewModel(),
    onRepoItemClick:()->Unit
){
    val repoListUiSate by viewModel.repoListUiState.collectAsStateWithLifecycle()

    RepoListScreen(
        repoListUiSate = repoListUiSate,
        onRepoItemClick = onRepoItemClick,
        onRefreshRepoList = viewModel::handleAction
    )
}

@Composable
fun RepoListScreen(
    repoListUiSate: RepoListUiState,
    onRepoItemClick:()->Unit,
    onRefreshRepoList: (RepoListUiAction) -> Unit
){
    ScaffoldTopAppbar(
        title = "Repo List",
        containerColor = MaterialTheme.color.secondaryBackground
    ) {
        val modifier = Modifier.padding(it)
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when(repoListUiSate){
                is RepoListUiState.Error -> {
                    NetworkErrorMessage(
                        message = repoListUiSate.message,
                        onClickRefresh = {
                            onRefreshRepoList(RepoListUiAction.FetchRepoList)
                        }
                    )
                }
                is RepoListUiState.HasRepoList -> {
                    LazyColumn{
                        items(items = repoListUiSate.repoList){repoItem->
                            RepoListItem(
                                repoItem = repoItem,
                                onItemClick = onRepoItemClick
                            )
                        }
                    }

                }
                RepoListUiState.Loading -> CircularProgressIndicator()
                RepoListUiState.RepoListEmpty -> Text(text = "No Repo List Found")
            }
        }
    }
}

@Composable
private fun RepoListItem(
    modifier: Modifier = Modifier,
    repoItem: RepoItemEntity,
    onItemClick:()->Unit
){
    Card(
        modifier = modifier.padding(bottom = 10.dp).clickable { onItemClick()},
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.color.white)
    ) {
        Column(
            modifier = modifier.padding(16.dp).fillMaxWidth()
        ) {
            Row(
                verticalAlignment =  Alignment.CenterVertically
            ) {
                Image(
                    painter =  rememberAsyncImagePainter(model = repoItem.userAvatarUrl),
                    contentDescription = "",
                    modifier = modifier.size(80.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )
                Spacer(modifier = modifier.width(16.dp))
                Column {
                    Text(text = repoItem.repoName,style = MaterialTheme.typography.titleMedium)
                    Text(text = repoItem.userName,style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = modifier.height(16.dp))
            Text(text = repoItem.repoFullName,style = MaterialTheme.typography.bodyMedium)
            Text(text = repoItem.repoDescription, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = modifier.height(16.dp))
            Row {
                Row(
                    modifier = modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Language,
                        contentDescription = ""
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = repoItem.language,style = MaterialTheme.typography.labelLarge)
                }
                Row(
                    modifier = modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.StarBorder, contentDescription = "")
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = "${repoItem.stargazers_count} Star", style = MaterialTheme.typography.labelLarge)
                }
                Row(
                    modifier = modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.ForkRight, contentDescription = "")
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = "${repoItem.forksCount} Forked", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}



