package com.yogesh.stylish.presentation.feature.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yogesh.stylish.domain.model.Product
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(text = "Error: ${state.error}", color = Color.Red)
            } else if (state.product != null) {
                ProductDetailsContent(product = state.product!!)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsContent(product: Product) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Image Pager
        item {
            val pagerState = rememberPagerState { product.images.size }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) { page ->
                AsyncImage(
                    model = product.images[page],
                    contentDescription = "Product Image ${page + 1}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Pager Indicator
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }
        }

        // 2. Product Info
        item {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = product.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = "Brand: ${product.brand}", style = MaterialTheme.typography.titleMedium, color = Color.Gray)

                // Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Rating: ${product.rating}", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    repeat(product.rating.roundToInt().coerceIn(0, 5)) {
                        Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color(0xFFFFC107))
                    }
                }

                // Price
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val newPrice = product.price * 10
                    val originalPrice = (newPrice / (1 - product.discountPercentage / 100)).roundToInt()

                    Text(text = "₹${newPrice.toInt()}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(text = "₹$originalPrice", textDecoration = TextDecoration.LineThrough, color = Color.Gray)
                    Text(text = "${product.discountPercentage.toInt()}% Off", color = Color(0xFF4CAF50), fontWeight = FontWeight.SemiBold)
                }

                // Stock
                val stockColor = if (product.stock > 0) Color(0xFF4CAF50) else Color.Red
                Text(text = if (product.stock > 0) "In Stock" else "Out of Stock", color = stockColor, fontWeight = FontWeight.Bold)

                // Description
                Text(text = "Description", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 16.dp))
                Text(text = product.description, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(onClick = { /* TODO: Add to Wishlist */ }, modifier = Modifier.weight(1f)) {
                        Text("Add to Wishlist")
                    }
                    Button(onClick = { /* TODO: Add to Cart */ }, modifier = Modifier.weight(1f)) {
                        Text("Add to Cart")
                    }
                }
            }
        }
    }
}