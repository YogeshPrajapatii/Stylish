package com.yogesh.stylish.presentation.ui.screens.mainscreens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.yogesh.stylish.presentation.navigation.Routes

data class BottomNavItem(

    val label: String, val icon: ImageVector, val route: Routes

)

val bottomNavItem = listOf(

    BottomNavItem(label = "Home", icon = Icons.Default.Home, route = Routes.HomeScreen),
    BottomNavItem(label = "Wishlist",
        icon = Icons.Default.FavoriteBorder,
        route = Routes.HomeScreen),
    BottomNavItem(label = "Cart", icon = Icons.Default.ShoppingCart, route = Routes.HomeScreen),
    BottomNavItem(label = "Search", icon = Icons.Default.Search, route = Routes.HomeScreen),
    BottomNavItem(label = "Setting", icon = Icons.Default.Settings, route = Routes.HomeScreen)


)


@Composable
fun MyBottomBar() {


}