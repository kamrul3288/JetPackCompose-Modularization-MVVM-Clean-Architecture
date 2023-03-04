package com.iamkamrul.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.iamkamrul.common.compose.ApplicationAppbar
import com.iamkamrul.common.compose.CircularProgressBar
import com.iamkamrul.common.compose.NetworkErrorMessage
import com.iamkamrul.common.compose.PaddingValuesBody
import com.iamkamrul.entity.ProfileEntity

@Composable
fun ProfileScreen(
    onClickBack:()->Unit,
    viewModel: ProfileScreenViewModel = hiltViewModel()
){
    Scaffold(topBar = {
        ApplicationAppbar(title = "Profile", onClickBack = onClickBack)
    }) {
        when(val result =  viewModel.uiState.collectAsStateWithLifecycle().value){
            is ProfileUiState.Error -> NetworkErrorMessage(message = result.message) {
                viewModel.action(ProfileUiAction.FetchProfile)
            }
            is ProfileUiState.Loading -> CircularProgressBar()
            is ProfileUiState.Success -> ProfileContentView(entity = result.profile)
        }

        PaddingValuesBody(paddingValues = it)
    }
}

@Preview
@Composable
fun PreviewProfileContentView(){
    ProfileContentView(entity = ProfileEntity())
}

@Composable
fun ProfileContentView(
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

