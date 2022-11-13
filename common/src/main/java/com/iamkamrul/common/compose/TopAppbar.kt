package com.iamkamrul.common.compose

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ApplicationAppbar(title:String){
    TopAppBar(
        title = {
            Text(text = title)
        }
    )
}

@Composable
fun ApplicationAppbar(
    title:String,
    onClickBack:()->Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = modifier.clickable {
                    onClickBack()
                }
            )
        }
    )
}