package com.yogesh.stylish.presentation.ui.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yogesh.stylish.R
import com.yogesh.stylish.presentation.navigation.Routes

@Composable
fun Splash(
    navController: NavController,
    splashViewModel: SplashViewModel,
) {

    val destination by splashViewModel.startupDestination.collectAsState()
    LaunchedEffect(destination) {
        destination?.let {
            when (it) {
                StartupDestination.Home -> navController.navigate(Routes.HomeScreen) {
                    popUpTo<Routes.Splash> {
                        inclusive = true
                    }
                }

                StartupDestination.Login -> navController.navigate(Routes.Login) {
                    popUpTo<Routes.Splash> {
                        inclusive = true
                    }
                }

                StartupDestination.Onboarding -> navController.navigate(Routes.OnBoarding1) {
                    popUpTo<Routes.Splash> {
                        inclusive = true
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {


            Image(painter = painterResource(id = R.drawable.img_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(

                text = "Stylish",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge

            )

        }
        

    }
}