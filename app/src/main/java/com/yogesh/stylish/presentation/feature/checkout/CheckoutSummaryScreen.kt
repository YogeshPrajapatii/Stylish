package com.yogesh.stylish.presentation.feature.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutSummaryScreen(
    navController: NavController,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Summary") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = {
                        viewModel.placeOrder {
                            navController.navigate(Routes.OrderSuccessScreen) {
                                popUpTo(Routes.HomeScreen) { inclusive = false }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = state.selectedAddress != null && state.cartItems.isNotEmpty()
                ) {
                    Text("Place Order & Pay ₹${state.finalPayable}", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Deliver to:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                state.selectedAddress?.let { address ->
                    Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), shape = RoundedCornerShape(12.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(address.fullName, fontWeight = FontWeight.Bold)
                            Text("${address.houseNumber}, ${address.area}")
                            Text("${address.city}, ${address.state} - ${address.pincode}")
                            Text("Phone: ${address.phoneNumber}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Price Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        PriceRow("Total Original Price", "₹${state.totalOriginalPrice}", isStrikethrough = true)
                        PriceRow("Product Discount", "- ₹${state.totalSavings}", color = Color(0xFF4CAF50))
                        PriceRow("Delivery Charges", if (state.shippingCharge == 0) "FREE" else "₹${state.shippingCharge}")
                        HorizontalDivider()
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            Text("Total Amount", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("₹${state.finalPayable}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: String, isStrikethrough: Boolean = false, color: Color = Color.Unspecified) {
    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
        Text(label)
        Text(
            text = value,
            color = color,
            textDecoration = if (isStrikethrough) TextDecoration.LineThrough else null
        )
    }
}