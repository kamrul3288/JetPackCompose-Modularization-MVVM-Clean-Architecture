package com.iamkamrul.repolist

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RepoListScreen(){
    Scaffold(
        backgroundColor = Color.LightGray,
        topBar = {
            RepoListTopAppBar()
        },
    ){
        LazyColumn{
            items(10){
                RepoListItem()
            }
        }
    }
}

//RepoList Item
@Composable
fun RepoListItem(modifier: Modifier = Modifier){
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
                   painter =  rememberAsyncImagePainter(model = "https://developer.android.com/codelabs/jetpack-compose-animation/img/ea1442f28b3c3b39.png"),
                   contentDescription = "",
                   modifier = modifier
                       .size(80.dp)
                       .aspectRatio(1f)
                       .clip(CircleShape)
                       .border(2.dp, Color.Gray, CircleShape)
               )
               Spacer(modifier = modifier.width(16.dp))
               Column {
                   Text(text = "Kamrul Hasan", fontWeight = FontWeight.Bold)
                   Text(text = "Android Jetpackcompose")
               }
            }

            Spacer(modifier = modifier.height(32.dp))
            Text(text = "Kamrul3288/Jetpack Compose")
            Text(text = "No Description found")

            Spacer(modifier = modifier.height(16.dp))
           Row {
               Row(
                   modifier = modifier.weight(1f),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(imageVector = Icons.Outlined.Info, contentDescription = "")
                   Spacer(modifier = modifier.width(4.dp))
                   Text(text = "Kotlin", fontWeight = FontWeight.Bold)
               }
               Row(
                   modifier = modifier.weight(1f),
                   horizontalArrangement = Arrangement.Center,
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(imageVector = Icons.Outlined.Star, contentDescription = "")
                   Spacer(modifier = modifier.width(4.dp))
                   Text(text = "14 Star", fontWeight = FontWeight.Bold)
               }
               Row(
                   modifier = modifier.weight(1f),
                   horizontalArrangement = Arrangement.End,
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Icon(imageVector = Icons.Outlined.Share, contentDescription = "")
                   Spacer(modifier = modifier.width(4.dp))
                   Text(text = "14 Forked", fontWeight = FontWeight.Bold)
               }
           }
        }
    }
}

@Composable
@Preview
fun PreviewRepoListItem(){
    RepoListItem()
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

@Composable
@Preview
fun PreviewRepoListTopAppBar(){
    RepoListTopAppBar()
}