package com.yogesh.stylish.presentation.feature.home.components


import androidx.compose.foundation.Image
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
import com.yogesh.stylish.presentation.theme.White 

@Composable
fun SummerSaleCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp) 
            .padding(horizontal = 10.dp, vertical = 8.dp), 
        shape = RoundedCornerShape(10.dp), 
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), 
        colors = CardDefaults.cardColors(containerColor = White) 
    ) {
        Image(
            painter = painterResource(id = R.drawable.summer_sale_img),
            contentDescription = "Hot Summer Sale",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth 
          
        )
       
    }
}