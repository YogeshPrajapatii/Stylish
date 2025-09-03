package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.yogesh.stylish.presentation.navigation.Routes


data class BottomNavItem(val label: String,
                         val selectedIcon: ImageVector,
                         val unselectedIcon: ImageVector,
                         val route: Routes
)


val bottomNavItems = listOf(BottomNavItem(label = "Home",
    selectedIcon = Icons.Filled.Home,
    unselectedIcon = Icons.Outlined.Home,
    route = Routes.HomeScreen),
    BottomNavItem(label = "Wishlist",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        route = Routes.HomeScreen),

    BottomNavItem(label = "Search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        route = Routes.HomeScreen),
    BottomNavItem(label = "Cart",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        route = Routes.HomeScreen),
    
    BottomNavItem(label = "Setting",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = Routes.HomeScreen))

@Composable
fun MyBottomBar(navController: NavController) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar() {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    // TODO: Abhi sab HomeScreen par jaa rahe hain, baad mein sahi route par bhejenge
                    navController.navigate(item.route) {
                        // Backstack ko a_che se manage karne ke liye
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                label = { Text(text = item.label) },
                icon = {
                    val icon =
                        if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon
                    Icon(imageVector = icon, contentDescription = item.label)
                },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer))
        }
    }
}