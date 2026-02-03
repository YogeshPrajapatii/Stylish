package com.yogesh.stylish.presentation.feature.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogesh.stylish.presentation.component.StylishButton
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
                title = { Text("Order Summary", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    StylishButton(
                        text = "Pay ₹${state.totalFinalPrice}",
                        onClick = {
                            viewModel.prepareOrder { amount, orderId ->
                                navController.navigate(Routes.PaymentScreen(amount, orderId))
                            }
                        },
                        enabled = state.selectedAddress != null && state.cartItems.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text("Shipping Address", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                state.selectedAddress?.let { address ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(address.fullName, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text("${address.houseNumber}, ${address.area}", color = Color.DarkGray)
                            Text("${address.city}, ${address.state} - ${address.pincode}", color = Color.DarkGray)
                            Text("Phone: ${address.phoneNumber}", modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }

            item {
                Text("Price Details", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        PriceRow(label = "Total MRP", value = "₹${state.totalOriginalPrice}")
                        PriceRow(label = "Discount", value = "-₹${state.totalSavings}", color = Color(0xFF4CAF50))
                        PriceRow(label = "Shipping Fee", value = "FREE", color = Color(0xFF4CAF50))
                        HorizontalDivider()
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            Text("Total Amount", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                            Text("₹${state.totalFinalPrice}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: String, color: Color = Color.Unspecified) {
    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold, color = color)
    }
}