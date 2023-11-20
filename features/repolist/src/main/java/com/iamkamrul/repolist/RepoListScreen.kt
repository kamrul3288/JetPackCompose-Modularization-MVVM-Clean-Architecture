package com.iamkamrul.repolist
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.iamkamrul.ui.component.CircularProgressBar
import com.iamkamrul.ui.component.NetworkErrorMessage
import com.iamkamrul.entity.RepoItemEntity

@Composable
fun RepoListScreen(
    uiState: RepoListUiState,
    onNavigateProfile:()->Unit,
    onRefreshRepoList:()->Unit
){
    RepoListScreen(
        uiState = uiState,
        hasRepoList = { repoItem, modifier ->
            RepoListItem(
                modifier = modifier,
                repoItem = repoItem,
                onItemClick = onNavigateProfile
            )
        },
        onRefreshRepoList = onRefreshRepoList
    )
}

@Composable
private fun RepoListScreen(
    uiState: RepoListUiState,
    hasRepoList: @Composable (repoItem:RepoItemEntity, modifier:Modifier) -> Unit,
    onRefreshRepoList:()->Unit
){
    Scaffold(
        topBar = { com.iamkamrul.designsystem.component.ApplicationAppbar(title = "Repo List") },
    ) {
        val modifier = Modifier.padding(it)
        FullScreenLoading(
            isLoading = uiState.isLoading,
            loadingContent = { CircularProgressBar() },
            content = {
                when(uiState){

                    is RepoListUiState.HasRepoList -> {
                        LazyColumn{
                            items(items = uiState.repoList){repoItem->
                                hasRepoList(repoItem,modifier)
                            }
                        }
                    }

                    is RepoListUiState.RepoListEmpty -> {
                        if (uiState.error.isNotEmpty()){
                            NetworkErrorMessage(
                                message = uiState.error,
                                onClickRefresh = onRefreshRepoList
                            )
                        }else{
                            Text(text = "You have no repo list right now")
                        }
                    }
                }
            }
        )


    }
}

@Composable
private fun FullScreenLoading(
    isLoading:Boolean,
    loadingContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
){
    if (isLoading) loadingContent()
    else content()
}

@Composable
private fun RepoListItem(
    modifier: Modifier = Modifier,
    repoItem: RepoItemEntity,
    onItemClick:()->Unit
){
    Card(
        modifier = modifier
            .padding(bottom = 1.dp)
            .clickable {
                onItemClick()
            },
        shape = RoundedCornerShape(size = 0.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment =  Alignment.CenterVertically
            ) {
                Image(
                    painter =  rememberAsyncImagePainter(model = repoItem.userAvatarUrl),
                    contentDescription = "",
                    modifier = modifier
                        .size(80.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = modifier.width(16.dp))
                Column {
                    Text(text = repoItem.userName, fontWeight = FontWeight.Bold)
                    Text(text = repoItem.repoName)
                }
            }

            Spacer(modifier = modifier.height(32.dp))
            Text(text = repoItem.repoFullName)
            Text(text = repoItem.repoDescription)

            Spacer(modifier = modifier.height(16.dp))
            Row {
                Row(
                    modifier = modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = "")
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = repoItem.language, fontWeight = FontWeight.Bold)
                }
                Row(
                    modifier = modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Star, contentDescription = "")
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = "${repoItem.stargazers_count} Star", fontWeight = FontWeight.Bold)
                }
                Row(
                    modifier = modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Share, contentDescription = "")
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = "${repoItem.forksCount} Forked", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}



