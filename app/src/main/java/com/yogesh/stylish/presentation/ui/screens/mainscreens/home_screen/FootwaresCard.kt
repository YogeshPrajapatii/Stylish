package com.yogesh.stylish.presentation.ui.screens.mainscreens.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface // Surface import karein
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R
import com.yogesh.stylish.presentation.ui.theme.FigmaRed // 👈 FigmaRed import karein
import com.yogesh.stylish.presentation.ui.theme.White // Pure White import karein
import com.yogesh.stylish.presentation.ui.theme.StylishBlack // StylishBlack for text

@Composable
fun FootwaresCard() {
    Surface( 
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(5),
        shadowElevation = 2.dp, 
        tonalElevation = 0.dp, 
        color = White 
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(16.dp) 
                    .fillMaxHeight() 
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_gold_bar), 
                    contentDescription = "Gold Bar",
                    modifier = Modifier.fillMaxSize(), 
                    contentScale = ContentScale.FillBounds 
                )
            }

          
            Box(
                modifier = Modifier
                    .weight(0.7f) 
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                 Image(
                     painter = painterResource(id = R.drawable.ic_deco_pattern), 
                     contentDescription = "Star Pattern Background",
                     modifier = Modifier.fillMaxSize(),
                     contentScale = ContentScale.FillBounds,
                     alpha = 0.5f 
                 )
                

                Image(
                    painter = painterResource(id = R.drawable.ic_heels), 
                    contentDescription = "Heels",
                    modifier = Modifier.size(130.dp)
                )
            }


            Column(
                modifier = Modifier
                    .weight(1.3f) 
                    .fillMaxHeight() 
                    .padding(end = 16.dp, start = 8.dp), 
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Flat and Heels",
                    style = MaterialTheme.typography.titleLarge, 
                    fontWeight = FontWeight.Bold,
                    color = StylishBlack 
                )
                Text(
                    text = "Stand a chance to get rewarded",
                    style = MaterialTheme.typography.bodyMedium, 
                    color = MaterialTheme.colorScheme.onSurfaceVariant 
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { /* Handle click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = FigmaRed), 
                    shape = RoundedCornerShape(8.dp) 
                ) {
                    Text(text = "Visit now ->", color = White) 
                }
            }
        }
    }
}