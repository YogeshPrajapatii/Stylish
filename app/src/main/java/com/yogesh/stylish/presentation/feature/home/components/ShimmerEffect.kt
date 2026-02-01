package com.yogesh.stylish.presentation.feature.home.components

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

@Composable
fun ShimmerEffect() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item { SearchAndFilterPlaceholder() }
        item { CategoryChipsPlaceholder() }
        item { PromoBannerPlaceholder() }
        item { ProductRowPlaceholder() }
        item { OfferCardsPlaceholder() }
        item { FootwaresCardPlaceholder() }
        item { ProductRowPlaceholder() }
        item { SummerSalePlaceholder() }
        item { SponsoredPlaceholder() }
    }
}

// --- Component Placeholders ---

@Composable
private fun SearchAndFilterPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ShimmerItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = CircleShape
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShimmerItem(modifier = Modifier.weight(1f), height = 24.dp, shape = RoundedCornerShape(6.dp))
            ShimmerItem(modifier = Modifier.width(60.dp), height = 24.dp, shape = RoundedCornerShape(6.dp))
        }
    }
}

@Composable
private fun CategoryChipsPlaceholder() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(5) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ShimmerItem(size = 64.dp, shape = CircleShape)
                ShimmerItem(height = 10.dp, width = 50.dp, shape = RoundedCornerShape(4.dp))
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
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun ProductRowPlaceholder() {
    Column(
        modifier = Modifier.padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ShimmerItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(2) {
                Column(modifier = Modifier.width(180.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    ShimmerItem(height = 130.dp, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp))
                    ShimmerItem(height = 10.dp, modifier = Modifier.fillMaxWidth(0.4f), shape = RoundedCornerShape(4.dp))
                    ShimmerItem(height = 14.dp, modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(4.dp))
                    ShimmerItem(height = 14.dp, modifier = Modifier.fillMaxWidth(0.7f), shape = RoundedCornerShape(4.dp))
                }
            }
        }
    }
}

@Composable
private fun OfferCardsPlaceholder() {
    ShimmerItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun FootwaresCardPlaceholder() {
    ShimmerItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun SummerSalePlaceholder() {
    ShimmerItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun SponsoredPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ShimmerItem(height = 20.dp, width = 120.dp, shape = RoundedCornerShape(6.dp))
        ShimmerItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun ShimmerItem(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    width: Dp? = null,
    size: Dp? = null,
    shape: Shape
) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutLinearInEasing),
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
