package com.yogesh.stylish.presentation.ui.screens.mainscreens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.presentation.navigation.Routes
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.CategoryChipsRow
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.FootwaresCard
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.HomeAppBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.HorizontalProductList
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.MyBottomBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.OfferCards
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.ProductsRow
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.PromoBanner
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.SearchAndFilterSection
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.ShimmerEffect
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.SponsoredCard
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.SummerSaleCard
import com.yogesh.stylish.presentation.ui.theme.StylishRed
import com.yogesh.stylish.presentation.ui.theme.White


// HomeScreen.kt

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.products) {
        if (state.products.isNotEmpty()) {
            val allCategoriesInProducts = state.products.map { it.category }.toSet()
            Log.d("HomeScreen_Debug",
                "PRODUCT CATEGORIES THAT ARRIVED USING API: $allCategoriesInProducts")
        }
    }

    Scaffold(

        topBar = { HomeAppBar() },
        bottomBar = { MyBottomBar(navController) },
        containerColor = Color.White

    ) { innerPadding ->


        if (state.isLoading) {

            Box(modifier = Modifier.padding(innerPadding)) {
                ShimmerEffect()
            }
        } else if (state.error != null) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
                contentAlignment = Alignment.Center) {
                Text(text = state.error!!)
            }
        } else {
            LazyColumn(modifier = Modifier
                .padding(innerPadding)
                .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(2.dp)) {
                item { SearchAndFilterSection() }
                item {
                    CategoryChipsRow(categories = state.categories, onCategoryClick = {})
                }
                item { PromoBanner() }
                item {
                    ProductsRow(title = "Deal of the Day",
                        products = state.products,
                        onViewAllClicked = {},
                        subtitle = "22h 55m 20s remaining",
                        icon = Icons.Default.Schedule,
                        headerContainerColor = Color(0xFF2196F3), //  Blue
                        headerContentColor = White,
                        onProductClick = { productId ->
                            navController.navigate(Routes.ProductDetailScreen(productId = productId))
                        })
                }
                item { OfferCards() }
                item {
                    val offerProducts = state.products.filter { it.discountPercentage > 15 }
                        .sortedByDescending { it.discountPercentage }
                    HorizontalProductList(offerProducts, onProductClick = { productId ->
                        navController.navigate(Routes.ProductDetailScreen(productId = productId))
                    })
                }

                item { FootwaresCard() }
                item {
                    val footwearProducts = state.products.filter { product ->
                        product.category == "mens-shoes" || product.category == "womens-shoes"
                    }
                    HorizontalProductList(products = footwearProducts,
                        onProductClick = { productId ->
                            navController.navigate(Routes.ProductDetailScreen(productId = productId))
                        })
                }

                item {
                    val trendingProducts = state.products.filter { it.rating > 4.5 }
                    ProductsRow(title = "Trending Products",
                        products = trendingProducts,
                        onViewAllClicked = {},
                        subtitle = "Last Date 29/02/22",
                        icon = Icons.Default.DateRange,
                        headerContainerColor = StylishRed, // Dark Red
                        headerContentColor = White,
                        onProductClick = { productId ->
                            navController.navigate(Routes.ProductDetailScreen(productId = productId))
                        })
                }

                item {
                    SummerSaleCard()
                }
                item {
                    SponsoredCard()
                }
                item { Spacer(modifier = Modifier.height(6.dp)) }

            }
        }
    }
}