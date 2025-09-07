package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R
import com.yogesh.stylish.presentation.ui.theme.AccentCardBackground
import com.yogesh.stylish.presentation.ui.theme.PromoCardBackground
import com.yogesh.stylish.presentation.ui.theme.PromoSolidBackground
import com.yogesh.stylish.presentation.ui.theme.White

@Composable
fun OfferCards() {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(horizontal = 10.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp,
        tonalElevation = 0.dp,
        color = White){
        Row(
           
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.special_offers_bg),
                contentDescription = "Special Offers Icon",
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Special Offers 😱",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "We make sure you get the best prices",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}