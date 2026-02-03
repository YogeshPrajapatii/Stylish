package com.yogesh.stylish.presentation.feature.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.model.finalPriceINR
import com.yogesh.stylish.domain.model.originalPriceINR
import com.yogesh.stylish.presentation.component.SizeSelector
import com.yogesh.stylish.presentation.component.StylishButton
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController, viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.resetSnackbarMessage()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }, actions = {
            IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
            }
        })
    }, bottomBar = {
        if (state.product != null) {
            ProductDetailBottomBar(
                isInWishlist = state.isInWishlist,
                isAlreadyInCart = state.isAlreadyInCart,
                isStockAvailable = state.product!!.stock > 0,
                onToggleWishlist = { viewModel.toggleWishlist() },
                onToggleCart = { viewModel.toggleCart() },
                onBuyNow = {
                    if (!state.isAlreadyInCart) viewModel.toggleCart()
                    navController.navigate(Routes.CartScreen)
                })
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(
                    text = state.error!!, color = MaterialTheme.colorScheme.error
                )

                state.product != null -> ProductDetailsContent(
                    product = state.product!!,
                    selectedSize = state.selectedSize,
                    onSizeSelected = { viewModel.onSizeSelected(it) })
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsContent(
    product: Product, selectedSize: String, onSizeSelected: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                val pagerState = rememberPagerState { product.images.size }
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.images[page]).crossfade(true).build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(product.images.size) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp)
                        )
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = product.brand,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Filled.Star,
                        null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = product.rating.toString(), fontWeight = FontWeight.SemiBold)
                    if (product.stock > 0) Text(text = "| In Stock", color = Color(0xFF4CAF50))
                }
                if (!product.sizes.isNullOrEmpty()) {
                    SizeSelector(
                        sizes = product.sizes,
                        selectedSize = selectedSize,
                        onSizeSelected = onSizeSelected
                    )
                }
                HorizontalDivider()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "₹${product.finalPriceINR}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (product.discountPercentage > 0) {
                        Text(
                            text = "₹${product.originalPriceINR}",
                            style = MaterialTheme.typography.titleMedium,
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                    }
                }
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ProductDetailBottomBar(
    isInWishlist: Boolean,
    isAlreadyInCart: Boolean,
    isStockAvailable: Boolean,
    onToggleWishlist: () -> Unit,
    onToggleCart: () -> Unit,
    onBuyNow: () -> Unit
) {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onToggleWishlist,
                shape = CircleShape,
                modifier = Modifier.size(52.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = if (isInWishlist) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isInWishlist) Color.Red else MaterialTheme.colorScheme.primary
                )
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                StylishButton(
                    text = "Buy Now",
                    onClick = onBuyNow,
                    enabled = isStockAvailable,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }
            OutlinedButton(
                onClick = onToggleCart,
                shape = CircleShape,
                modifier = Modifier.size(52.dp),
                contentPadding = PaddingValues(0.dp),
                enabled = isStockAvailable
            ) {
                Icon(
                    imageVector = if (isAlreadyInCart) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                    contentDescription = null,
                    tint = if (isAlreadyInCart) Color.Red else MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}