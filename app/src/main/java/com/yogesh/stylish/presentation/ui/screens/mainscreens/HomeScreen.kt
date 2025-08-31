package com.yogesh.stylish.presentation.ui.screens.mainscreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.CategoryChipsRow
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.HomeAppBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.MyBottomBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.ProductsRow
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.PromoBanner
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.SearchAndFilterSection


// HomeScreen.kt

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(

        topBar = { HomeAppBar() }, bottomBar = { MyBottomBar(navController) }

    ) { innerPadding ->


        if (state.isLoading) {
            Box(
                // padding lagayein taaki loading icon bhi top/bottom bar ke neeche aaye
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
                // Yahan par hum baad mein Shimmer Effect laga sakte hain
            }
        }
        // Yadi koi error hai...
        else if (state.error != null) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
                contentAlignment = Alignment.Center) {
                Text(text = state.error!!)
            }
        }
        // Yadi sab theek hai aur data aa chuka hai...
        else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item { SearchAndFilterSection() }
                item {
                    CategoryChipsRow(categories = state.categories, onCategoryClick = {})
                }
                item { PromoBanner() }
                item {
                    ProductsRow(title = "Deal of the Day",
                        products = state.products,
                        onViewAllClicked = {})
                }
                // item { FootwaresCard() } // Yaad rakhein, agle step mein ise hatana hai
                item {
                    val trendingProducts = state.products.filter { it.rating > 4.5 }
                    ProductsRow(title = "Trending Products",
                        products = trendingProducts,
                        onViewAllClicked = {})
                }
            }
        }
    }
}