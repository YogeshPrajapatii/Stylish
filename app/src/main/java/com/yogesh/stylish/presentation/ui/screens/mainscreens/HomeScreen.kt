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
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.FootwaresCard
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.HomeAppBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.MyBottomBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.ProductsRow
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.PromoBanner
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.SearchAndFilterSection

@Composable
fun HomeScreen(
    navController: NavHostController,

    ) {

    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { HomeAppBar() },
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->

        // Handle the Loading state
        if (state.isLoading && state.products.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Handle the Error state
        state.error?.let { errorMessage ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = errorMessage)
            }
        }

        // When data is available, show the main content
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            item { SearchAndFilterSection() }

            // Pass the real categories list to the component
            item {
                CategoryChipsRow(
                    categories = state.categories,
                    onCategoryClick = {}
                )
            }

            item { PromoBanner() }

            // Pass the real products list to the component
            item {
                ProductsRow(
                    title = "Deal of the Day",
                    products = state.products, // Using real data from the state!
                    onViewAllClicked = {}
                )
            }

            item { FootwaresCard() }

            // Example of reusing ProductsRow with filtered data
            item {
                val trendingProducts = state.products.filter { it.rating > 4.5 }
                ProductsRow(
                    title = "Trending Products",
                    products = trendingProducts,
                    onViewAllClicked = {}
                )
            }
        }
    }
}