package com.yogesh.stylish.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yogesh.stylish.presentation.ui.screens.authscreens.ForgotPassword
import com.yogesh.stylish.presentation.ui.screens.authscreens.Login
import com.yogesh.stylish.presentation.ui.screens.authscreens.ResetPassword
import com.yogesh.stylish.presentation.ui.screens.authscreens.SignUp
import com.yogesh.stylish.presentation.ui.screens.mainscreens.HomeScreen
import com.yogesh.stylish.presentation.ui.screens.onboardingscreens.OnBoarding1
import com.yogesh.stylish.presentation.ui.screens.onboardingscreens.OnBoarding2
import com.yogesh.stylish.presentation.ui.screens.onboardingscreens.OnBoarding3
import com.yogesh.stylish.presentation.ui.screens.splashscreen.Splash

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Splash) {

        composable<Routes.Splash> {
            Splash(navController = navController)
        }

        composable<Routes.OnBoarding1> {
            OnBoarding1(navController = navController)
        }
        composable<Routes.OnBoarding2> {
            OnBoarding2(navController = navController)
        }
        composable<Routes.OnBoarding3> {
            OnBoarding3(navController = navController)
        }

        composable<Routes.SignUp> {
            SignUp(navController = navController)
        }
        composable<Routes.Login> {
            Login(navController = navController)
        }

        composable<Routes.ForgotPassword> {
            ForgotPassword(navController = navController)
        }
        composable<Routes.ResetPassword> {
            ResetPassword(navController = navController)
        }

        composable<Routes.HomeScreen> {
            HomeScreen(navController = navController)
        }
    }
}