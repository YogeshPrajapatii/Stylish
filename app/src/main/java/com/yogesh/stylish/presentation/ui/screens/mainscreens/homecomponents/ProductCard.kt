package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R

@Composable
fun ProductCard() {
    Card(modifier = Modifier.padding(8.dp)) {

        Column() {
            Image(painter = painterResource(id = R.drawable.obone),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop)

        }
        
        Column (modifier = Modifier.padding(8.dp)){

            Text(text = "Product Name")
            Text(text = "Product Price")
            
        }

    }
}

@Preview
@Composable
fun ProductCardPreview() {
    ProductCard()
}