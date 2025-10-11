package com.yogesh.stylish.presentation.ui.screens.mainscreens.profile_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val state by profileViewModel.state.collectAsState()


    LaunchedEffect(state.didLogout) {

        if (state.didLogout) {

            navController.navigate(Routes.Login) {

                popUpTo(navController.graph.id) {
                    inclusive = true
                }

            }

            state.error?.let { errorMessage ->
                Log.e("Profile Screen", "Logout Error : $errorMessage")

            }


        }


    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("My Profile") },
            // Correct placement for navigationIcon parameter
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back")
                }
            })
    }) { paddingValues -> // This is the padding that needs to be used
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Apply the padding here
            .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {
            // Top section: User information
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(modifier = Modifier.size(120.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Icon",
                    tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(24.dp))

                if (state.userEmail != null) {
                    Text(text = state.userEmail!!, fontSize = 22.sp, fontWeight = FontWeight.Medium)
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                }
            }

            // Bottom section: Logout Button
            Button(onClick = { profileViewModel.logout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                enabled = !state.didLogout) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text("LOGOUT", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

    }
}
