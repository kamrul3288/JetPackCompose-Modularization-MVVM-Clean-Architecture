package com.iamkamrul.common.compose

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun ApplicationAppbar(title:String){
    TopAppBar(
        title = {
            Text(text = title)
        }
    )
}