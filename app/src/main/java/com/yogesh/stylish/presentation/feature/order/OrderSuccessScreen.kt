package com.yogesh.stylish.presentation.feature.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yogesh.stylish.presentation.component.StylishButton
import com.yogesh.stylish.presentation.navigation.Routes

@Composable
fun OrderSuccessScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = Color(0xFF4CAF50)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Payment Successful!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Your order has been placed successfully.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))

        StylishButton(
            text = "Continue Shopping",
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Routes.HomeScreen) {
                    popUpTo(Routes.HomeScreen) { inclusive = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                navController.navigate(Routes.OrderHistoryScreen) {
                    popUpTo(Routes.HomeScreen)
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) {
            Text("View My Orders", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}