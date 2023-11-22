package com.iamkamrul.profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.iamkamrul.designsystem.component.ScaffoldTopAppbar
import com.iamkamrul.ui.component.NetworkErrorMessage
import com.iamkamrul.entity.ProfileEntity

@Composable
internal fun ProfileScreenRoute(
    viewModel:ProfileViewModel = hiltViewModel(),
    onBackBtnClick:()->Unit
){
    val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    ProfileScreen(
        profileUiState = profileUiState,
        onRefreshProfile = viewModel::handleAction,
        onBackBtnClick = onBackBtnClick
    )
}

@Composable
private fun ProfileScreen(
    profileUiState: ProfileUiState,
    onRefreshProfile:(ProfileUiAction)->Unit,
    onBackBtnClick:()->Unit
){
    ScaffoldTopAppbar(
        title = "Profile",
        onNavigationIconClick = onBackBtnClick
    ) {
        val modifier = Modifier.padding(it)
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            when(profileUiState){
                is ProfileUiState.Error -> NetworkErrorMessage(message = profileUiState.message){
                    onRefreshProfile(ProfileUiAction.FetchProfile)
                }
                ProfileUiState.Loading -> CircularProgressIndicator()
                is ProfileUiState.Success -> ProfileContentView(data = profileUiState.data)
            }
        }
    }
}

@Composable
private fun ProfileContentView(
    data: ProfileEntity,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter =  rememberAsyncImagePainter(model = data.userAvatar),
            contentDescription = "",
            modifier = modifier
                .size(80.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = modifier.height(16.dp))
        Text(text = data.userFullName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = data.userName)
        Spacer(modifier = modifier.height(16.dp))
        Text(text = data.about)

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
                Text(text = data.repoCount.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Repository")
            }

            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = data.followerCount.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Follower")
            }


            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = data.followingCount.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Following")
            }
        }
    }
}

