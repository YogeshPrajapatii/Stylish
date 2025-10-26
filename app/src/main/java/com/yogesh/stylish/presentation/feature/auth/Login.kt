package com.yogesh.stylish.presentation.feature.auth

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.R
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.presentation.navigation.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = hiltViewModel()
    var userId by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val authState by authViewModel.authState.collectAsState()
    val isLoading = authState is Result.Loading

    LaunchedEffect(authState) {
        when (val currentState = authState) {
            is Result.Failure -> {
                Toast.makeText(context, currentState.message, Toast.LENGTH_SHORT).show()
            }
            is Result.Success -> {
                navController.navigate(Routes.HomeScreen) {
                    popUpTo(Routes.Login) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .windowInsetsPadding(WindowInsets.statusBars),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Welcome \nBack!",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                )

                OutlinedTextField(
                    value = userId,
                    onValueChange = { userId = it },
                    label = { Text("Username or Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )

                Text(
                    "Forgot Password?",
                    modifier = Modifier
                        .clickable { navController.navigate(Routes.ForgotPassword) }
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    textAlign = TextAlign.End,
                    color = Color.Red
                )

                Button(
                    onClick = {
                        if (userId.isNotBlank() && password.isNotBlank()) {
                            authViewModel.login(userId, password)
                        } else {
                            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    },
                    // <-- FIX: Added contentColor = Color.White
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White, // Ensure spinner is also white
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        // FIX: Explicitly set text color to ensure visibility
                        Text("Login", color = Color.White)
                    }
                }

                Text("- or continue with -")

                Row(
                    modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SocialLoginButton(R.drawable.ic_google, "Google")
                    SocialLoginButton(R.drawable.ic_apple, "Apple")
                    SocialLoginButton(R.drawable.ic_facebook, "Facebook")
                }

                Row {
                    Text("Create an account ")
                    Text(
                        "Sign Up",
                        modifier = Modifier.clickable { navController.navigate(Routes.SignUp) },
                        color = Color.Blue
                    )
                }
            }
        }
    }
}

@Composable
fun SocialLoginButton(iconRes: Int, contentDescription: String) {
    IconButton(onClick = { /* TODO: Social login logic */ }) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(32.dp)
        )
    }
}