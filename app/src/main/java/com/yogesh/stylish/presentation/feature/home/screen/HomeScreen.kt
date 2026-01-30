package com.yogesh.stylish.presentation.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.presentation.navigation.Routes
import com.yogesh.stylish.presentation.feature.home.components.*
import com.yogesh.stylish.presentation.feature.product.ProductCard
import com.yogesh.stylish.presentation.feature.product.ProductsRow
import com.yogesh.stylish.presentation.theme.StylishRed
import com.yogesh.stylish.presentation.theme.White

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { HomeAppBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        containerColor = Color.White
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color.White)) {

            SearchAndFilterSection(
                query = state.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChanged(it) }
            )

            if (state.isLoading) {
                ShimmerEffect()
            } else if (state.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.error!!)
                }
            } else {
                if (state.searchQuery.isNotEmpty()) {
                    SearchSuggestionsGrid(state, navController)
                } else {
                    HomeContentFeed(state, navController)
                }
            }
        }
    }
}

@Composable
fun SearchSuggestionsGrid(state: HomeScreenState, navController: NavHostController) {
    if (state.filteredProducts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No products found for '${state.searchQuery}'", color = Color.Gray)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(state.filteredProducts) { product ->
                ProductCard(product = product, onProductClick = {
                    navController.navigate(Routes.ProductDetailScreen(it))
                })
            }
        }
    }
}

@Composable
fun HomeContentFeed(state: HomeScreenState, navController: NavHostController) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        item {
            CategoryChipsRow(
                categories = state.categories,
                onCategoryClick = { category ->
                    navController.navigate(Routes.CategoryProductScreen(categoryName = category.name))
                }
            )
        }
        item { PromoBanner() }
        item {
            ProductsRow(
                title = "Deal of the Day",
                products = state.products,
                subtitle = "22h 55m 20s remaining",
                icon = Icons.Default.Schedule,
                headerContainerColor = Color(0xFF2196F3),
                headerContentColor = White,
                onProductClick = { navController.navigate(Routes.ProductDetailScreen(it)) },
                onViewAllClicked = {}
            )
        }
        item { OfferCards() }
        item {
            val offerProducts = state.products.filter { it.discountPercentage > 15 }
                .sortedByDescending { it.discountPercentage }
            HorizontalProductList(offerProducts, onProductClick = {
                navController.navigate(Routes.ProductDetailScreen(it))
            })
        }
        item { FootwaresCard() }
        item {
            val footwearProducts = state.products.filter { it.category.contains("shoes") }
            HorizontalProductList(footwearProducts, onProductClick = {
                navController.navigate(Routes.ProductDetailScreen(it))
            })
        }
        item {
            val trendingProducts = state.products.filter { it.rating > 4.5 }
            ProductsRow(
                title = "Trending Products",
                products = trendingProducts,
                subtitle = "Limited Time Offer",
                icon = Icons.Default.DateRange,
                headerContainerColor = StylishRed,
                headerContentColor = White,
                onProductClick = { navController.navigate(Routes.ProductDetailScreen(it)) },
                onViewAllClicked = {}
            )
        }
        item { SummerSaleCard() }
        item { SponsoredCard() }
        item { Spacer(modifier = Modifier.height(10.dp)) }
    }
}