package com.yogesh.stylish.presentation.ui.screens.mainscreens.home_screen

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This ShimmerEffect's shapes and sizes now accurately match the HomeScreen UI.
 */
@Composable
fun ShimmerEffect() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Same spacing as HomeScreen
    ) {
        // 1. Placeholder for SearchAndFilterSection
        item {
            Spacer(modifier = Modifier.height(8.dp)) // Added top spacing
            SearchAndFilterPlaceholder()
        }

        // 2. Placeholder for CategoryChipsRow
        item {
            CategoryChipsPlaceholder()
        }

        // 3. Placeholder for PromoBanner
        item {
            PromoBannerPlaceholder()
        }

        // 4. Placeholder for "Deal of the Day" ProductsRow
        item {
            ProductRowPlaceholder()
        }

        // 5. Placeholder for OfferCards
        item {
            OfferCardsPlaceholder()
        }

        // 6. Placeholder for HorizontalProductList (Offers)
        item {
            HorizontalProductListPlaceholder()
        }

        // 7. Placeholder for FootwaresCard
        item {
            FootwaresCardPlaceholder()
        }

        // 8. Placeholder for HorizontalProductList (Footwear)
        item {
            HorizontalProductListPlaceholder()
        }

        // 9. Placeholder for "Trending Products" ProductsRow
        item {
            ProductRowPlaceholder()
        }
    }
}

// --- Component Placeholders ---

@Composable
private fun SearchAndFilterPlaceholder() {
    ShimmerItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp), // Height of your actual TextField
        shape = RoundedCornerShape(12.dp) // <-- FIX: Less rounded corners, adjust as per your actual TextField
    )
}

@Composable
private fun CategoryChipsPlaceholder() {
    // CORRECTED: Using circular shapes for images and rectangles for text below.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround // SpaceAround gives better distribution
    ) {
        repeat(5) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerItem(size = 64.dp, shape = CircleShape) // Corrected to CircleShape
                ShimmerItem(height = 12.dp, width = 50.dp, shape = RoundedCornerShape(4.dp))
            }
        }
    }
}

@Composable
private fun PromoBannerPlaceholder() {
    ShimmerItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun ProductRowPlaceholder() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Section Header Placeholder - More accurate now
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ShimmerItem(height = 20.dp, width = 150.dp, shape = RoundedCornerShape(6.dp))
                ShimmerItem(height = 14.dp, width = 200.dp, shape = RoundedCornerShape(6.dp))
            }
            ShimmerItem(height = 16.dp, width = 60.dp, shape = RoundedCornerShape(6.dp))
        }
        HorizontalProductListPlaceholder()
    }
}

@Composable
private fun OfferCardsPlaceholder() {
    ShimmerItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun FootwaresCardPlaceholder() {
    ShimmerItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun HorizontalProductListPlaceholder() {
    // CORRECTED: More accurate product card skeleton.
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(2) {
            Column(modifier = Modifier.width(180.dp)) {
                // Image part of the card
                ShimmerItem(height = 124.dp, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(12.dp))
                // Text lines part of the card
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ShimmerItem(height = 16.dp, modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(4.dp))
                    ShimmerItem(height = 14.dp, modifier = Modifier.fillMaxWidth(0.5f), shape = RoundedCornerShape(4.dp))
                    ShimmerItem(height = 14.dp, modifier = Modifier.fillMaxWidth(0.7f), shape = RoundedCornerShape(4.dp))
                }
            }
        }
    }
}


// --- Base Shimmer Composable ---
@Composable
private fun ShimmerItem(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    width: Dp? = null,
    size: Dp? = null,
    shape: Shape
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ShimmerAnim"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    var sizeModifier = modifier
    size?.let { sizeModifier = sizeModifier.size(it) }
    width?.let { sizeModifier = sizeModifier.width(it) }
    height?.let { sizeModifier = sizeModifier.height(it) }

    Spacer(
        modifier = sizeModifier
            .clip(shape)
            .background(brush)
    )
}