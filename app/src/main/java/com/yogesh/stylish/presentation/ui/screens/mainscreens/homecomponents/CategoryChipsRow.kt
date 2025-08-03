package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class Category(

    val name: String, val imgRes: Int
)

@Composable
fun CategoryChip(category: Category, onClick: () -> Unit) {

    Column(modifier = Modifier
        .padding(horizontal = 8.dp)
        .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Card(modifier = Modifier.size(60.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))) {

            Box(modifier = Modifier.fillMaxSize(),

                contentAlignment = Alignment.Center) {

                Image(painter = painterResource(id = category.imgRes),
                    contentDescription = category.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp))

            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = category.name, Modifier.size(14.dp))


    }

}



@Composable
fun CategoryChipsRow(){

}