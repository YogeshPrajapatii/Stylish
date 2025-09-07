package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R
import com.yogesh.stylish.presentation.ui.theme.White

@Composable
fun SponsoredCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp) 
            .padding(horizontal = 10.dp, vertical = 8.dp), 
        shape = RoundedCornerShape(10.dp), 
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), 
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.supponserd_img), 
            contentDescription = "Sponsored Offer",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds 
            
        )
    }
}