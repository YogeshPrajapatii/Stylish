package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun ShimmerEffect() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Placeholder for SearchAndFilterSection
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                ShimmerItem(modifier = Modifier.fillMaxWidth().height(56.dp), cornerRadius = 28.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ShimmerItem(height = 24.dp, width = 120.dp, cornerRadius = 8.dp)
                    Spacer(modifier = Modifier.weight(1f))
                    ShimmerItem(height = 40.dp, width = 80.dp, cornerRadius = 20.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    ShimmerItem(height = 40.dp, width = 80.dp, cornerRadius = 20.dp)
                }
            }
        }

        // 2. Placeholder for CategoryChipsRow
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(5) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ShimmerItem(size = 64.dp, shape = CircleShape)
                        ShimmerItem(height = 12.dp, width = 50.dp, cornerRadius = 4.dp)
                    }
                }
            }
        }

        // 3. Placeholder for PromoBanner
        item {
            ShimmerItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp),
                cornerRadius = 16.dp
            )
        }

        // 4. Placeholder for "Deal of the Day" ProductsRow
        item {
            ProductRowPlaceholder()
        }

        // 5. Placeholder for OfferCards
        item {
            ShimmerItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp),
                cornerRadius = 16.dp
            )
        }

        // 6. Placeholder for FootwaresCard
        item {
            ShimmerItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(horizontal = 16.dp),
                cornerRadius = 16.dp
            )
        }

        // 7. Placeholder for HorizontalProductList (Footwear)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(2) {
                    ShimmerItem(
                        modifier = Modifier.width(180.dp).height(280.dp),
                        cornerRadius = 12.dp
                    )
                }
            }
        }


        // 8. Placeholder for "Trending Products" ProductsRow
        item {
            ProductRowPlaceholder()
        }
    }
}

// ProductsRow ka placeholder banane ke liye ek alag Composable
@Composable
private fun ProductRowPlaceholder() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Section Header Placeholder
        ShimmerItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            cornerRadius = 12.dp
        )
        // Product Cards Placeholder
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerItem(
                modifier = Modifier
                    .weight(1f)
                    .height(280.dp),
                cornerRadius = 12.dp
            )
            ShimmerItem(
                modifier = Modifier
                    .weight(1f)
                    .height(280.dp),
                cornerRadius = 12.dp
            )
        }
    }
}

// Yeh helper component waisa hi hai, bas thoda saaf kiya hai
@Composable
private fun ShimmerItem(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    width: Dp? = null,
    size: Dp? = null,
    cornerRadius: Dp = 0.dp,
    shape: Shape = RoundedCornerShape(cornerRadius)
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
        ), label = "ShimmerAnim"
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