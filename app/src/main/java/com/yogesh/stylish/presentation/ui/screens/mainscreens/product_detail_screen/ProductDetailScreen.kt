package com.yogesh.stylish.presentation.ui.screens.mainscreens.product_detail_screen 

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yogesh.stylish.domain.model.Product 
import com.yogesh.stylish.presentation.ui.screens.mainscreens.viewmodel.ProductDetailViewModel 
import kotlin.math.roundToInt 

@OptIn(ExperimentalMaterial3Api::class) 
@Composable
fun ProductDetailScreen(
    productId: Int, 
    navController: NavHostController,
    viewModel: ProductDetailViewModel = hiltViewModel() 
    
) {
    val state by viewModel.state.collectAsState() 

    LaunchedEffect(productId) {
        viewModel.getProductById(productId) 
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.product?.title ?: "") }, 
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
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) 
            } else if (state.error != null) {
                Text(text = "Error: ${state.error}", modifier = Modifier.align(Alignment.Center)) 
            } else if (state.product != null) {
                ProductDetailContent(product = state.product!!) 
            } else {
                Text(text = "Product not found", modifier = Modifier.align(Alignment.Center)) 
            }
        }
    }
}

@Composable
fun ProductDetailContent(product: Product) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.images.firstOrNull() ?: product.thumbnail) 
                    .crossfade(true).build(),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = product.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val newPrice = product.price * 10 
                    val originalPrice = (newPrice / (1 - product.discountPercentage / 100)).roundToInt()

                    Text(
                        text = "₹${newPrice.toInt()}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "₹$originalPrice",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = "${product.discountPercentage.toInt()}% Off",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Description:", style = MaterialTheme.typography.titleMedium)
                Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Divider(modifier = Modifier.fillMaxWidth().height(1.dp), color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Details:", style = MaterialTheme.typography.titleMedium)
                Text(text = "Brand: ${product.brand}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Category: ${product.category}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Stock: ${product.stock}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Rating: ${product.rating}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Add to Cart logic */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Add to Cart")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Buy Now logic */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Buy Now")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}