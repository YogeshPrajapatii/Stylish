package com.yogesh.stylish.presentation.feature.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.presentation.component.StylishButton
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.didLogout) {
        state.didLogout?.let { result ->
            when (result) {
                is Result.Success -> {
                    navController.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
                is Result.Failure -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.ProfileEditScreen) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Box(modifier = Modifier.size(100.dp)) {
                if (state.profileInfo?.imageUri != null) {
                    AsyncImage(
                        model = state.profileInfo!!.imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = state.profileInfo?.fullName ?: "Set Your Name",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = state.userEmail ?: "", color = Color.Gray)

            Spacer(Modifier.height(32.dp))

            ProfileMenuItem("My Orders", Icons.Default.ShoppingBag) {
                navController.navigate(Routes.OrderHistoryScreen)
            }
            ProfileMenuItem("My Addresses", Icons.Default.LocationOn) {
                navController.navigate(Routes.AddressListScreen)
            }

            Spacer(modifier = Modifier.weight(1f))

            StylishButton(
                text = "LOGOUT",
                onClick = { viewModel.logout() })
        }
    }
}

@Composable
fun ProfileMenuItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
            }
            Icon(Icons.AutoMirrored.Filled.NavigateNext, null, tint = Color.Gray)
        }
    }
}