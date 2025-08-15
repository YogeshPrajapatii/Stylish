package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This composable now just displays a single category name as a chip.
 * It takes a simple String as a parameter.
 */
@Composable
fun CategoryChip(categoryName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)) // Light gray
    ) {
        Text(
            text = categoryName.replaceFirstChar { it.uppercase() }, // Capitalize the first letter
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * This composable now accepts a List of Strings (the category names) from the ViewModel.
 */
@Composable
fun CategoryChipsRow(
    categories: List<String>,
    onCategoryClick: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // It loops through the list of category names
        items(categories) { categoryName ->
            // And creates a CategoryChip for each one
            CategoryChip(
                categoryName = categoryName,
                onClick = { onCategoryClick(categoryName) }
            )
        }
    }
}