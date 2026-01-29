package com.yogesh.stylish.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    @Serializable
    data object Splash : Routes

    @Serializable
    data object OnBoarding1 : Routes

    @Serializable
    data object OnBoarding2 : Routes

    @Serializable
    data object OnBoarding3 : Routes

    @Serializable
    data object SignUp : Routes

    @Serializable
    data object Login : Routes

    @Serializable
    data object ForgotPassword : Routes

    @Serializable
    data object HomeScreen : Routes

    @Serializable
    data object ResetPassword : Routes
    
    @Serializable
    data class ProductDetailScreen(val productId: Int) : Routes    
    @Serializable
    data object ProfileScreen: Routes

    @Serializable
    data object WishlistScreen : Routes

    @Serializable
    data object CartScreen : Routes

    @Serializable
    object AddAddressScreen : Routes

    @Serializable
    object AddressListScreen : Routes

    @Serializable
    object CheckoutSummaryScreen : Routes

    @Serializable object OrderSuccessScreen : Routes
    @Serializable object OrderHistoryScreen : Routes
    @Serializable object ProfileEditScreen : Routes


    
}