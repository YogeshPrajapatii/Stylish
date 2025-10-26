package com.yogesh.stylish.presentation.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yogesh.stylish.presentation.navigation.Routes
import com.yogesh.stylish.presentation.theme.StylishBlack
import com.yogesh.stylish.presentation.theme.StylishRed
import com.yogesh.stylish.presentation.theme.White

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
    BottomNavItem(label = "Cart",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        route = Routes.HomeScreen),
    BottomNavItem(label = "Search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        route = Routes.HomeScreen),
    BottomNavItem(label = "Setting",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = Routes.HomeScreen))


@Composable
fun MyBottomBar(navController: NavController) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))

        NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = White,
            modifier = Modifier.fillMaxWidth()) {
            bottomNavItems.forEachIndexed { index, item ->
                val isSelected = selectedItemIndex == index
                NavigationBarItem(

                    selected = isSelected,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(Routes.HomeScreen) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(text = item.label,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else StylishBlack)
                    },
                    icon = {
                        if (item.label == "Cart" && isSelected) {
                            Box(modifier = Modifier
                                .size(45.dp)
                                .background(color = StylishRed, shape = CircleShape),
                                contentAlignment = Alignment.Center) {
                                Icon(imageVector = item.selectedIcon,
                                    contentDescription = item.label,
                                    tint = White)
                            }
                        } else {
                            Icon(imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else StylishBlack)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent))
            }
        }
    }


}