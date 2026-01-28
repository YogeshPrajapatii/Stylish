package com.yogesh.stylish.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SizeSelector(
    sizes: List<String>,
    selectedSize: String,
    onSizeSelected: (String) -> Unit
) {
    if (sizes.isEmpty()) return

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Select Size",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(sizes) { size ->
                val isSelected = size == selectedSize

                Box(
                    modifier = Modifier
                        .size(height = 45.dp, width = 60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else Color.Transparent
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onSizeSelected(size) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = size,
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}