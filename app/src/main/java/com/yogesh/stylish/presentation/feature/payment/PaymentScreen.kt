package com.yogesh.stylish.presentation.feature.payment

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.razorpay.Checkout
import com.yogesh.stylish.MainActivity
import com.yogesh.stylish.presentation.navigation.Routes
import org.json.JSONObject

@Composable
fun PaymentScreen(
    navController: NavController,
    amount: Int,
    orderId: Int,
    viewModel: PaymentViewModel = hiltViewModel(LocalContext.current as MainActivity)
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            viewModel.resetState()
            navController.navigate(Routes.OrderSuccessScreen) {
                popUpTo(Routes.CartScreen) { inclusive = true }
            }
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.resetState()
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setOrderId(orderId)
        viewModel.startProcessing()

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_SE4dKbEoO9wMgh")

        try {
            val options = JSONObject()
            options.put("name", "Stylish App")
            options.put("description", "Order #$orderId")
            options.put("theme.color", "#E44336")
            options.put("currency", "INR")
            options.put("amount", amount * 100)
            options.put("prefill.email", "yogesh@example.com")
            options.put("prefill.contact", "9898989898")

            checkout.open(activity, options)
        } catch (e: Exception) {
            viewModel.handlePaymentResult(false, e.message)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Processing Payment...",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}