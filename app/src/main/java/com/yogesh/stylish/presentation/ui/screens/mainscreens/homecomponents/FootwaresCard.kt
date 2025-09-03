package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R
import com.yogesh.stylish.presentation.ui.theme.StylishRed

@Composable
fun FootwaresCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp) // 1. Card ko ek consistent, aakarshak height di
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // 2. Figma jaisa safed background
    ) {
        Row(
            // 3. Row ko poori height di
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Side: Image aur Decorative pattern ke liye Box
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f), // Dono hisson ko barabar jagah di
                contentAlignment = Alignment.Center
            ) {
                // Decorative Pattern (Peeche)
                Image(
                    painter = painterResource(id = R.drawable.ic_deco_pattern),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight()
                )
                // Heels ki Image (Upar)
                Image(
                    painter = painterResource(id = R.drawable.ic_heels),
                    contentDescription = "Heels"
                )
            }

            // Right Column (Text aur Button ke liye)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center, // Content ko vertically center kiya
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Flat and Heels",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Stand a chance to get rewarded",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = StylishRed),
                    shape = RoundedCornerShape(25)
                ) {
                    Text(text = "Visit now ->")
                }
            }
        }
    }
}