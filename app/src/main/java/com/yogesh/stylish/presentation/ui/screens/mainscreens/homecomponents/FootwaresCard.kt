package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogesh.stylish.R

@Composable
fun FootwaresCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Left Image
            Image(painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Heels",
                modifier = Modifier.size(120.dp))
            // Right Column with Text and Button
            Column(modifier = Modifier
                .padding(16.dp)
                .weight(1f)) {
                Text(text = "Flat and Heels", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Stand a chance to get rewarded", fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { }) {
                    Text(text = "Visit now ->")
                }
            }
        }
    }
}