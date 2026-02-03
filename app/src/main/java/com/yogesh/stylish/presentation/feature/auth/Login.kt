package com.yogesh.stylish.presentation.feature.auth

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.R
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.presentation.component.StylishButton
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

    Scaffold(containerColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Welcome \nBack!",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 42.sp
                )

                Spacer(modifier = Modifier.height(36.dp))

                OutlinedTextField(
                    value = userId,
                    maxLines = 1,
                    onValueChange = { userId = it },
                    label = { Text("Username or Email") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Password Input
                OutlinedTextField(
                    value = password,
                    maxLines = 1,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                )

                Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .clickable { navController.navigate(Routes.ForgotPassword) }
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    textAlign = TextAlign.End,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(36.dp))

                StylishButton(
                    text = if (isLoading) "Loading..." else "Login",
                    enabled = !isLoading,
                    onClick = {
                        if (userId.isNotBlank() && password.isNotBlank()) {
                            authViewModel.login(userId, password)
                        } else {
                            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    })
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "- OR Continue with -",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SocialLoginButton(R.drawable.ic_google, "Google")
                    SocialLoginButton(R.drawable.ic_apple, "Apple")
                    SocialLoginButton(R.drawable.ic_facebook, "Facebook")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Create An Account ", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "Sign Up",
                        modifier = Modifier.clickable { navController.navigate(Routes.SignUp) },
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SocialLoginButton(iconRes: Int, contentDescription: String) {
    Surface(
        onClick = { /* Handle Social Login */ },
        shape = CircleShape,
        border = BorderStroke(1.dp, Color(0xFFFDE9EA)),
        modifier = Modifier.size(54.dp),
        color = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = contentDescription,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}