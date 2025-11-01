package com.yogesh.stylish.presentation.feature.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart 
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.presentation.navigation.Routes
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
                title = { Text("Product Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
      
        bottomBar = {
            if (state.product != null) {
                ProductDetailBottomBar(
                    product = state.product!!,
                    isInWishlist = state.isInWishlist,
                    onToggleWishlist = { viewModel.toggleWishlist() },
                    onAddToCart = { viewModel.addToCart() }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background 
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.error != null -> {
                    Text(text = "Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                }
                state.product != null -> {
                    ProductDetailsContent(product = state.product!!)
                }
                else -> {
                    Text("Product not found.")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsContent(product: Product) {
    // LazyColumn now fills the whole space
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        // Add padding at the bottom so content doesn't hide under the sticky buttons
        contentPadding = PaddingValues(bottom = 100.dp)
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
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.images[page])
                        .crossfade(true)
                        .build(),
                    contentDescription = "Product Image ${page + 1}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Use Crop or Fit based on your design
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
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
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
                // Apply Theme Typography
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineSmall, // Use Theme
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Brand: ${product.brand}",
                    style = MaterialTheme.typography.titleMedium, // Use Theme
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Use Theme
                )

                // Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Rating: ${product.rating}",
                        style = MaterialTheme.typography.bodyMedium, // Use Theme
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    repeat(product.rating.roundToInt().coerceIn(0, 5)) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFFC107), // Star color is usually fine to hardcode
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Price
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // TODO: This price logic seems off. DummyJson price is usually final.
                    // Let's use the actual price fields.
                    val discountedPrice = product.price * (1 - product.discountPercentage / 100.0)
                    val originalPrice = product.price

                    Text(
                        text = "₹${(discountedPrice * 83).roundToInt()}", // TODO: Use proper currency conversion/locale
                        style = MaterialTheme.typography.headlineSmall, // Use Theme
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if(product.discountPercentage > 0) {
                        Text(
                            text = "₹${(originalPrice * 83).roundToInt()}", // TODO: Use proper currency conversion
                            style = MaterialTheme.typography.bodyMedium, // Use Theme
                            textDecoration = TextDecoration.LineThrough,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${product.discountPercentage.roundToInt()}% Off",
                            style = MaterialTheme.typography.bodyMedium, // Use Theme
                            color = Color(0xFF4CAF50), // TODO: Move to Color.kt as SuccessGreen
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Stock
                val stockColor = if (product.stock > 0) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error // Use Theme
                Text(
                    text = if (product.stock > 0) "In Stock (${product.stock})" else "Out of Stock",
                    style = MaterialTheme.typography.bodyMedium, // Use Theme
                    color = stockColor,
                    fontWeight = FontWeight.Bold
                )

                // Description
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleLarge, // Use Theme
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge // Use Theme
                )

                // Buttons are removed from here
            }
        }
    }
}

/**
 * This is the new composable for the sticky bottom bar.
 * It contains the Wishlist and Cart buttons.
 */
@Composable
fun ProductDetailBottomBar(
    product: Product,
    isInWishlist: Boolean,
    onToggleWishlist: () -> Unit,
    onAddToCart: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp, // Add shadow like Figma
        color = MaterialTheme.colorScheme.surface // Use theme color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding inside the bar
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Wishlist Button (Styled like "Outlined" or Figma's "View Similar")
            OutlinedButton(
                onClick = onToggleWishlist,
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = if (isInWishlist) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = if (isInWishlist) "In Wishlist" else "Wishlist",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp) // Match style
                )
            }

            // Add to Cart Button (Styled like "Filled" or Figma's "Buy Now")
            Button(
                onClick = onAddToCart,
                modifier = Modifier.weight(1f),
                enabled = product.stock > 0, // Disable if out of stock
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary // Use theme color
                    // You can define a custom "Buy Now" green in Color.kt if needed
                )
            ) {
                Text(
                    text = if (product.stock > 0) "Add to Cart" else "Out of Stock",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp) // Match style
                )
            }
        }
    }
}