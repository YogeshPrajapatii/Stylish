package com.yogesh.stylish.presentation.feature.cart // Adjust package as needed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogesh.stylish.presentation.feature.cart.components.CartBottomBar
import com.yogesh.stylish.presentation.feature.cart.components.CartItemCard
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel() // Inject the ViewModel
) {
    // Observe the state from the ViewModel
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Show "Clear All" button only if cart is not empty and not loading
                    if (state.cartItems.isNotEmpty() && !state.isLoading) {
                        TextButton(onClick = {
                            viewModel.clearCart() // Call ViewModel function
                        }) {
                            Text("Clear All", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        },
        // Show BottomBar only if cart is not empty and not loading
        bottomBar = {
            if (state.cartItems.isNotEmpty() && !state.isLoading) {
                CartBottomBar(
                    totalPrice = state.totalPrice,
                    totalItems = state.totalItems,
                    onCheckout = {
                        // TODO: Navigate to Checkout flow
                    }
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Apply Scaffold padding
            contentAlignment = Alignment.Center
        ) {
            when {
                // 1. Loading State
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                // 2. Error State
                state.error != null -> {
                    Text(
                        text = state.error ?: "An error occurred",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // 3. Empty State
                state.cartItems.isEmpty() -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Your cart is empty",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Button(onClick = { navController.navigate(Routes.HomeScreen) }) {
                            Text("Start Shopping")
                        }
                    }
                }

                // 4. Success State (List of items)
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.cartItems) { cartItem ->
                            CartItemCard(
                                cartItem = cartItem,
                                onProductClick = {
                                    navController.navigate(Routes.ProductDetailScreen(cartItem.product.id))
                                },
                                onQuantityIncrease = {
                                    viewModel.updateQuantity(cartItem.product.id, cartItem.quantity + 1)
                                },
                                onQuantityDecrease = {
                                    // ViewModel's updateQuantity handles quantity > 0 check
                                    viewModel.updateQuantity(cartItem.product.id, cartItem.quantity - 1)
                                },
                                onRemove = {
                                    viewModel.removeFromCart(cartItem.product.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}