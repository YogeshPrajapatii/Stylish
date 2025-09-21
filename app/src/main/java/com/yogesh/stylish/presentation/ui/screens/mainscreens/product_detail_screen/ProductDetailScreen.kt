package com.yogesh.stylish.presentation.ui.screens.mainscreens.product_detail_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.presentation.ui.screens.mainscreens.viewmodel.ProductDetailViewModel
import kotlin.math.roundToInt

// Figma design colors
val PrimaryRed = Color(0xFFE53935)
val OrangeTint = Color(0xFFFF9800)
val DarkGrayText = Color(0xFF333333)
val LightGrayBackground = Color(0xFFF0F0F0)
val DiscountGreen = Color(0xFF4CAF50)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int,
                        navController: NavHostController,
                        viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Shop page", style = MaterialTheme.typography.titleLarge) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Handle Cart Click */ }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent,
                titleContentColor = DarkGrayText,
                navigationIconContentColor = DarkGrayText,
                actionIconContentColor = DarkGrayText))
    }) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(text = "Error: ${state.error}", modifier = Modifier.align(Alignment.Center))
            } else if (state.product != null) {
                ProductDetailContent(product = state.product!!, navController = navController)
            } else {
                Text(text = "Product not found", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailContent(product: Product, navController: NavHostController) {
    val availableSizes = remember { listOf("6 UK", "7 UK", "8 UK", "9 UK", "10 UK") }
    var selectedSize by remember { mutableStateOf<String?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            val images = product.images.ifEmpty { listOf(product.thumbnail) }
            val pagerState = rememberPagerState(pageCount = { images.size })

            Column {
                HorizontalPager(state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)) { page ->
                    AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(images[page])
                        .crossfade(true).build(),
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        contentScale = ContentScale.Crop)
                }
                Row(Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) PrimaryRed else Color.LightGray
                        Box(modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp))
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                if (availableSizes.isNotEmpty()) {
                    Text(text = "Size: ${selectedSize ?: "Select"}",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                        color = DarkGrayText)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(availableSizes) { size ->
                            SizeChip(size = size,
                                isSelected = size == selectedSize,
                                onClick = { selectedSize = it })
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(text = product.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = DarkGrayText)
                Text(text = "${product.brand} Men's Shoes Size (All Colours)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(product.rating.roundToInt().coerceIn(0, 5)) {
                        Icon(imageVector = Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = OrangeTint,
                            modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${product.rating} (56,890)", // Assuming 56,890 is a static number for now
                        style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val newPrice = product.price * 10
                    val originalPrice =
                        (newPrice / (1 - product.discountPercentage / 100)).roundToInt()

                    Text(text = "₹${newPrice.toInt()}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkGrayText)
                    Text(text = "₹$originalPrice",
                        style = MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough),
                        color = Color.Gray)
                    Text(text = "${product.discountPercentage.toInt()}% Off",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryRed)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Description:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DarkGrayText)
                Text(text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkGrayText,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                TextButton(onClick = { /* Expand description */ }) {
                    Text("...More", color = PrimaryRed)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoChip(text = "Nearest Store", icon = Icons.Filled.LocationOn)
                    InfoChip(text = "VIP", icon = Icons.Filled.Stars)
                    InfoChip(text = "Return policy", icon = Icons.Filled.Policy)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    Button(onClick = { /* Go to Cart logic */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightGrayBackground,
                            contentColor = DarkGrayText),
                        shape = RoundedCornerShape(8.dp)) {
                        Text("Go to cart", style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { /* Buy Now logic */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DiscountGreen,
                            contentColor = Color.White),
                        shape = RoundedCornerShape(8.dp)) {
                        Text("Buy Now", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                DeliveryTimeCard(deliveryTime = "1 within Hour")
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    OutlinedButton(onClick = { /* Handle View Similar */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkGrayText),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp,
                            brush = SolidColor(Color.Gray)),
                        shape = RoundedCornerShape(8.dp)) {
                        Icon(Icons.Filled.Visibility,
                            contentDescription = "View Similar",
                            modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("View Similar", style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = { /* Handle Add to Compare */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkGrayText),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp,
                            brush = SolidColor(Color.Gray)),
                        shape = RoundedCornerShape(8.dp)) {
                        Icon(Icons.Filled.CompareArrows,
                            contentDescription = "Add to Compare",
                            modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add to Compare", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Similar To",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = DarkGrayText)
                Text(text = "282+ Items",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = { /* Handle Sort */ },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkGrayText),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp,
                            brush = SolidColor(Color.Gray)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)) {
                        Icon(Icons.Filled.Sort,
                            contentDescription = "Sort",
                            modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sort", style = MaterialTheme.typography.labelLarge)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = { /* Handle Filter */ },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkGrayText),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp,
                            brush = SolidColor(Color.Gray)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)) {
                        Icon(Icons.Filled.FilterList,
                            contentDescription = "Filter",
                            modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Filter", style = MaterialTheme.typography.labelLarge)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                DummySimilarProductList(navController = navController)
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun SizeChip(size: String, isSelected: Boolean, onClick: (String) -> Unit) {
    Surface(shape = RoundedCornerShape(8.dp),
        color = if (isSelected) PrimaryRed else LightGrayBackground,
        modifier = Modifier
            .clickable { onClick(size) }
            .border(width = 1.dp,
                color = if (isSelected) PrimaryRed else Color.Transparent,
                shape = RoundedCornerShape(8.dp))) {
        Text(text = size,
            color = if (isSelected) Color.White else DarkGrayText,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun InfoChip(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(shape = RoundedCornerShape(8.dp),
        color = LightGrayBackground,
        modifier = Modifier
            .clickable { /* Handle info click */ }
            .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))) {
        Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon,
                contentDescription = text,
                tint = DarkGrayText,
                modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, color = DarkGrayText, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun DeliveryTimeCard(deliveryTime: String) {
    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PrimaryRed.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.LocalShipping,
                contentDescription = "Delivery Time",
                tint = PrimaryRed)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Delivery in $deliveryTime",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = PrimaryRed)
        }
    }
}

@Composable
fun DummySimilarProductList(navController: NavHostController) {
    val dummyProducts = remember {
        listOf(
            Product(101,
                "Nike Air Jordan 1 Low Mystic Black",
                "Men's Shoes",
                1990.0,
                10.0,
                4.2,
                50,
                "Nike",
                "mens-shoes",
                "https://i.dummyjson.com/data/products/59/thumbnail.jpg",
                emptyList(),
                listOf("7 UK", "8 UK")),
            Product(102,
                "Nike Sneakers",
                "Mid Peach Macho Shoes for Men White Black Pink S...",
                1500.0,
                30.0,
                4.5,
                30,
                "Adidas",
                "sport-shoes",
                "https://i.dummyjson.com/data/products/59/1.jpg",
                emptyList(),
                listOf("7 UK", "8 UK")),
            Product(103,
                "Puma Running",
                "Running Shoes",
                2200.0,
                40.0,
                4.0,
                60,
                "Puma",
                "womens-shoes",
                "https://i.dummyjson.com/data/products/2/thumbnail.jpg",
                emptyList(),
                listOf("7 UK", "8 UK")), // Changed image
            Product(104,
                "Reebok Casual",
                "Casual Shoes",
                1200.0,
                20.0,
                3.8,
                40,
                "Reebok",
                "mens-shoes",
                "https://i.dummyjson.com/data/products/3/thumbnail.jpg",
                emptyList(),
                listOf("7 UK", "8 UK")), // Changed image
        )
    }

    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(dummyProducts) { product ->
            Card(modifier = Modifier
                .width(160.dp)
                .clickable {
                    navController.navigate("product_detail/${product.id}")
                },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                Column {
                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail).crossfade(true).build(),
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                        contentScale = ContentScale.Crop)
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = product.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = DarkGrayText)
                        Text(text = product.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            val newPrice = product.price * 10
                            val originalPrice =
                                (newPrice / (1 - product.discountPercentage / 100)).roundToInt()

                            Text(text = "₹${newPrice.toInt()}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = DarkGrayText)
                            Text(text = "₹$originalPrice",
                                style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                                color = Color.Gray)
                            Text(text = "${product.discountPercentage.toInt()}% Off",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryRed)
                        }
                    }
                }
            }
        }
    }
}