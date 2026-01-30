package com.yogesh.stylish.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yogesh.stylish.presentation.feature.address.AddAddressScreen
import com.yogesh.stylish.presentation.feature.address.AddressListScreen
import com.yogesh.stylish.presentation.feature.auth.ForgotPassword
import com.yogesh.stylish.presentation.feature.auth.Login
import com.yogesh.stylish.presentation.feature.auth.ResetPassword
import com.yogesh.stylish.presentation.feature.auth.SignUp
import com.yogesh.stylish.presentation.feature.cart.CartScreen
import com.yogesh.stylish.presentation.feature.checkout.CheckoutSummaryScreen
import com.yogesh.stylish.presentation.feature.checkout.OrderSuccessScreen
import com.yogesh.stylish.presentation.feature.home.screen.HomeScreen
import com.yogesh.stylish.presentation.feature.profile.ProfileScreen
import com.yogesh.stylish.presentation.feature.product.ProductDetailScreen
import com.yogesh.stylish.presentation.feature.onboarding.OnBoarding1
import com.yogesh.stylish.presentation.feature.onboarding.OnBoarding2
import com.yogesh.stylish.presentation.feature.onboarding.OnBoarding3
import com.yogesh.stylish.presentation.feature.orders.OrderHistoryScreen
import com.yogesh.stylish.presentation.feature.payment.PaymentScreen
import com.yogesh.stylish.presentation.feature.profile.ProfileEditScreen
import com.yogesh.stylish.presentation.feature.splash.Splash
import com.yogesh.stylish.presentation.feature.splash.SplashViewModel
import com.yogesh.stylish.presentation.feature.wishlist.WishlistScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Splash) {
        composable<Routes.Splash> {
            val splashViewModel: SplashViewModel = hiltViewModel()
            Splash(navController = navController, splashViewModel = splashViewModel)
        }
        composable<Routes.OnBoarding1> { OnBoarding1(navController = navController) }
        composable<Routes.OnBoarding2> { OnBoarding2(navController = navController) }
        composable<Routes.OnBoarding3> { OnBoarding3(navController = navController) }
        composable<Routes.SignUp> { SignUp(navController = navController) }
        composable<Routes.Login> { Login(navController = navController) }
        composable<Routes.ForgotPassword> { ForgotPassword(navController = navController) }
        composable<Routes.ResetPassword> { ResetPassword(navController = navController) }
        composable<Routes.HomeScreen> { HomeScreen(navController = navController) }
        composable<Routes.WishlistScreen> { WishlistScreen(navController = navController) }
        composable<Routes.ProfileScreen> { ProfileScreen(navController = navController) }
        composable<Routes.ProductDetailScreen> { ProductDetailScreen(navController = navController) }
        composable<Routes.CartScreen> { CartScreen(navController = navController) }
        composable<Routes.AddressListScreen> { AddressListScreen(navController = navController) }
        composable<Routes.AddAddressScreen> { AddAddressScreen(navController = navController) }
        composable<Routes.CheckoutSummaryScreen> { CheckoutSummaryScreen(navController = navController) }
        composable<Routes.OrderHistoryScreen> { OrderHistoryScreen(navController = navController) }
        composable<Routes.OrderSuccessScreen> { OrderSuccessScreen(navController = navController) }
        composable<Routes.ProfileEditScreen> { ProfileEditScreen(navController = navController) }
        composable<Routes.PaymentScreen> { backStackEntry ->
            val paymentRoute: Routes.PaymentScreen = backStackEntry.toRoute()
            PaymentScreen(
                navController = navController,
                amount = paymentRoute.amount,
                orderId = paymentRoute.orderId
            )
        }
    }
}