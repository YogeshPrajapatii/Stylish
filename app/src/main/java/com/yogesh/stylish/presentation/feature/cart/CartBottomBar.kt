package com.yogesh.stylish.presentation.feature.cart.components // Adjust package as needed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CartBottomBar(
    totalPrice: Double,
    totalItems: Int,
    onCheckout: () -> Unit
) {
    // Surface provides elevation and background color from the theme
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp, // Add shadow to make it pop
        color = MaterialTheme.colorScheme.surface // Use theme surface color
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Total Price and Item Count
                Column {
                    Text(
                        text = "Total ($totalItems ${if (totalItems == 1) "item" else "items"})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "₹${totalPrice.toInt()}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Checkout Button
                Button(
                    onClick = onCheckout,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f) // Give button flexible width
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Checkout",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}