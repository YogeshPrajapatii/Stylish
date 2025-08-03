package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.yogesh.stylish.presentation.navigation.Routes

data class BottomNavItem(

    val label: String, val icon: ImageVector, val route: Routes

)

val bottomNavItems = listOf(

    BottomNavItem(label = "Home", icon = Icons.Default.Home, route = Routes.HomeScreen),
    BottomNavItem(label = "Wishlist",
        icon = Icons.Default.FavoriteBorder,
        route = Routes.HomeScreen),
    BottomNavItem(label = "Cart", icon = Icons.Default.ShoppingCart, route = Routes.HomeScreen),
    BottomNavItem(label = "Search", icon = Icons.Default.Search, route = Routes.HomeScreen),
    BottomNavItem(label = "Setting", icon = Icons.Default.Settings, route = Routes.HomeScreen)


)


@Composable
fun MyBottomBar(navController: NavController) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {

        bottomNavItems.forEachIndexed { index, item ->

            NavigationBarItem(

                selected = selectedItemIndex == index, onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },

                label = { Text(text = item.label) },

                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                }

            )


        }

    }

}