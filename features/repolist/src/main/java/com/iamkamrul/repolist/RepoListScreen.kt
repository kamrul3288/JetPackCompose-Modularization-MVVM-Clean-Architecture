package com.iamkamrul.repolist
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.iamkamrul.entity.RepoItemEntity

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RepoListScreen(
    viewModel:RepoListViewModel = hiltViewModel()
){
    Scaffold(
        backgroundColor = Color.LightGray,
        topBar = {
            RepoListTopAppBar()
        },
    ){
        when(val result =  viewModel.uiState.collectAsStateWithLifecycle().value){
            is RepoListUiState.Error -> ErrorMessage(message = result.message)
            is RepoListUiState.Loading -> Progressbar()
            is RepoListUiState.Success -> {
                LazyColumn{
                    items(
                        items = result.repoList,
                    ){
                        RepoListItem(entity = it)
                    }
                }
            }
        }
        AppBody(paddingValues = it)
    }
}

@Composable
fun AppBody(paddingValues: PaddingValues){}

//RepoList Item
@Composable
fun RepoListItem(
    modifier: Modifier = Modifier,
    entity: RepoItemEntity
){
    Card(
        modifier = modifier.padding(bottom = 1.dp),
        elevation = 1.dp,
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
                   painter =  rememberAsyncImagePainter(model = entity.userAvatarUrl),
                   contentDescription = "",
                   modifier = modifier
                       .size(80.dp)
                       .aspectRatio(1f)
                       .clip(CircleShape)
                       .border(2.dp, Color.Gray, CircleShape)
               )
               Spacer(modifier = modifier.width(16.dp))
               Column {
                   Text(text = entity.userName, fontWeight = FontWeight.Bold)
                   Text(text = entity.repoName)
               }
            }

            Spacer(modifier = modifier.height(32.dp))
            Text(text = entity.repoFullName)
            Text(text = entity.repoDescription)

            Spacer(modifier = modifier.height(16.dp))
           Row {
               Row(
                   modifier = modifier.weight(1f),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(imageVector = Icons.Outlined.Info, contentDescription = "")
                   Spacer(modifier = modifier.width(4.dp))
                   Text(text = entity.language, fontWeight = FontWeight.Bold)
               }
               Row(
                   modifier = modifier.weight(1f),
                   horizontalArrangement = Arrangement.Center,
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(imageVector = Icons.Outlined.Star, contentDescription = "")
                   Spacer(modifier = modifier.width(4.dp))
                   Text(text = "${entity.stargazers_count} Star", fontWeight = FontWeight.Bold)
               }
               Row(
                   modifier = modifier.weight(1f),
                   horizontalArrangement = Arrangement.End,
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(imageVector = Icons.Outlined.Share, contentDescription = "")
                   Spacer(modifier = modifier.width(4.dp))
                   Text(text = "${entity.forksCount} Forked", fontWeight = FontWeight.Bold)
               }
           }
        }
    }
}

//Repo List Appbar
@Composable
fun RepoListTopAppBar(){
    TopAppBar(
        title = {
            Text(text = "RepoList")
        }
    )
}

//Progressbar
@Composable
fun Progressbar(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

//error
@Composable
fun  ErrorMessage(message:String){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = message)
    }
}