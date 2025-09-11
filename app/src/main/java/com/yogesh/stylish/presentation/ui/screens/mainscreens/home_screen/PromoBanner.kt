package com.yogesh.stylish.presentation.ui.screens.mainscreens.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R

@Composable
fun PromoBanner() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .padding(horizontal = 16.dp, vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(

                painter = painterResource(id = R.drawable.promo_banner_background),
                contentDescription = "Promotional Banner Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
            Column(modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(24.dp)) {
                Text(text = "50-40% OFF",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White)
                Text(text = "Now in (product)\nAll colours",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f))
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(onClick = { },
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(25),

                    border = BorderStroke(1.5.dp, Color.White),
                    contentPadding = PaddingValues(horizontal = 8.dp)) {
                    Text(text = "Shop Now ->", color = Color.White)
                }

            }
        }
    }
}