package com.yogesh.stylish.presentation.feature.address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressListScreen(
    navController: NavController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var selectedAddressId by remember { mutableIntStateOf(-1) }

    LaunchedEffect(state.addresses) {
        if (selectedAddressId == -1 && state.addresses.isNotEmpty()) {
            selectedAddressId = state.addresses.find { it.isDefault }?.id ?: state.addresses.first().id
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Address", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.AddAddressScreen) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }

                if (state.addresses.isNotEmpty()) {
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate(Routes.CheckoutSummaryScreen) },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        text = { Text("Proceed to Checkout") },
                        icon = { }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.addresses.isEmpty()) {
                Text("No addresses found. Add one!", modifier = Modifier.align(Alignment.Center), color = Color.Gray)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.addresses) { address ->
                        AddressSelectionCard(
                            address = address,
                            isSelected = selectedAddressId == address.id,
                            onSelect = { selectedAddressId = address.id },
                            onDelete = { viewModel.deleteAddress(address) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
fun AddressSelectionCard(
    address: AddressEntity,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onSelect() },
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = isSelected, onClick = onSelect)
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = address.fullName, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red.copy(alpha = 0.6f))
                    }
                }
                Text(text = "${address.houseNumber}, ${address.area}", style = MaterialTheme.typography.bodySmall)
                Text(text = "${address.city}, ${address.state} - ${address.pincode}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}