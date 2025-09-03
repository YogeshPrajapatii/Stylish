package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogesh.stylish.domain.model.Product
import kotlin.math.roundToInt

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // 1. Root layout ab ek Column hai (vertical)
        Column {
            // IMAGE WALA HISSA
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = product.title,
                // 2. Image ko hamesha consistent shape dene ke liye
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp), 
                contentScale = ContentScale.Fit 
            )

            // TEXT WALA HISSA
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Title
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Description (Figma design jaisa)
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Price Row (Figma design jaisa)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val originalPrice = (product.price / (1 - product.discountPercentage / 100)).roundToInt()

                    Text(
                        text = "₹${product.price.toInt()}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "₹$originalPrice",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,                      
                        textDecoration = TextDecoration.LineThrough // Kata hua text
                    )
                    Text(
                        text = "${product.discountPercentage.toInt()}% Off",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary // Reddish color for discount
                    )
                }

                // Rating Row (Figma design jaisa)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(product.rating.roundToInt().coerceIn(0, 5)) { // Rating 0-5 ke beech rahegi
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFFC107), // Yellow Star
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}