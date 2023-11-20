package com.iamkamrul.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.iamkamrul.common.compose.ApplicationAppbar
import com.iamkamrul.common.compose.CircularProgressBar
import com.iamkamrul.common.compose.NetworkErrorMessage
import com.iamkamrul.entity.ProfileEntity

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onRefreshProfile:()->Unit,
    onPopBack:()->Unit
){
    ProfileScreen(
        uiState = uiState,
        success = {profileEntity, modifier ->
           ProfileContentView(entity = profileEntity, modifier = modifier)
        },
        error = {message ->
            NetworkErrorMessage(
                message = message,
                onClickRefresh = onRefreshProfile
            )
        },
        onPopBack = onPopBack
    )
}

@Composable
private fun ProfileScreen(
    uiState: ProfileUiState,
    success: @Composable (profileEntity: ProfileEntity, modifier:Modifier) -> Unit,
    error: @Composable (message: String) -> Unit,
    onPopBack:()->Unit
){
    Scaffold(topBar = {
        ApplicationAppbar(
            title = "Profile",
            onClickBack = onPopBack
        )
    }) {
        val modifier = Modifier.padding(it)
        FullScreenLoading(
            isLoading = uiState.isLoading,
            loadingContent = { CircularProgressBar()},
            content = {
                when(uiState){
                    is ProfileUiState.Error -> error(uiState.error)
                    is ProfileUiState.Success -> success(uiState.profileEntity,modifier)
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
private fun ProfileContentView(
    modifier: Modifier = Modifier,
    entity: ProfileEntity
){
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter =  rememberAsyncImagePainter(model = entity.userAvatar),
            contentDescription = "",
            modifier = modifier
                .size(80.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = modifier.height(16.dp))
        Text(text = entity.userFullName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = entity.userName)
        Spacer(modifier = modifier.height(16.dp))
        Text(text = entity.about)

        Spacer(modifier = modifier.height(8.dp))
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray))
        Spacer(modifier = modifier.height(8.dp))

        Row(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = entity.repoCount.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Repository")
            }

            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = entity.followerCount.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Follower")
            }


            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = entity.followingCount.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Following")
            }
        }
    }
}

