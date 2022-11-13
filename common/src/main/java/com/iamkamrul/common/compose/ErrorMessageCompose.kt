package com.iamkamrul.common.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun  NetworkErrorMessage(
    message:String,
    modifier: Modifier = Modifier,
    onClickRefresh:()->Unit
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {

        Text(text = message, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = modifier.height(24.dp))
        Button(
            modifier = modifier.fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 54.dp),
            onClick = onClickRefresh,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Refresh")
        }
    }
}
