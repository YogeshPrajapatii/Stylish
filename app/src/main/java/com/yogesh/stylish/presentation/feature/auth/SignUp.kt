package com.yogesh.stylish.presentation.feature.auth

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun SignUp(navController: NavHostController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = hiltViewModel()
    var userId by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisibleSignUp by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisibleSignUp by rememberSaveable { mutableStateOf(false) }
    val authState by authViewModel.authState.collectAsState()
    val isLoading = authState is Result.Loading

    LaunchedEffect(authState) {
        when (val currentState = authState) {
            is Result.Failure -> {
                Toast.makeText(context, currentState.message, Toast.LENGTH_SHORT).show()
            }

            is Result.Success<*> -> {
                navController.navigate(Routes.HomeScreen) {
                    popUpTo(navController.graph.id) { inclusive = true }
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
                    text = "Create an\nAccount",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
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
                    visualTransformation = if (passwordVisibleSignUp) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisibleSignUp) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description =
                            if (passwordVisibleSignUp) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisibleSignUp = !passwordVisibleSignUp }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (confirmPasswordVisibleSignUp) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (confirmPasswordVisibleSignUp) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description =
                            if (confirmPasswordVisibleSignUp) "Hide password" else "Show password"
                        IconButton(onClick = {
                            confirmPasswordVisibleSignUp = !confirmPasswordVisibleSignUp
                        }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )

                Button(
                    onClick = {
                        if (userId.isNotBlank() && password.isNotBlank()) {
                            if (password == confirmPassword) {
                                authViewModel.signup(userId, password)
                            } else {
                                Toast.makeText(context,
                                    "Passwords do not match!",
                                    Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT)
                                .show()
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
                        Text("Sign Up", color = Color.White)
                    }
                }

                Text("- or continue with -", style = MaterialTheme.typography.bodyLarge)

                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp,
                        Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* TODO: Google login */ }) {
                        Image(painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Google Logo",
                            modifier = Modifier.size(32.dp))
                    }
                    IconButton(onClick = { /* TODO: Apple login */ }) {
                        Image(painter = painterResource(id = R.drawable.ic_apple),
                            contentDescription = "Apple Logo",
                            modifier = Modifier.size(32.dp))
                    }
                    IconButton(onClick = { /* TODO: Facebook login */ }) {
                        Image(painter = painterResource(id = R.drawable.ic_facebook),
                            contentDescription = "Facebook Logo",
                            modifier = Modifier.size(32.dp))
                    }
                }

                Row {
                    Text("I already have an account, ", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Login",
                        modifier = Modifier.clickable { navController.navigate(Routes.Login) },
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}