package com.yogesh.stylish.presentation.ui.screens.mainscreens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.CategoryChipsRow
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.FootwaresCard
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.HomeAppBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.MyBottomBar
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.Product
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.ProductsRow
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.PromoBanner
import com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents.SearchAndFilterSection

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = { HomeAppBar() },
        bottomBar = { MyBottomBar(navController = navController) }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            item { SearchAndFilterSection() }
            item { CategoryChipsRow() }
            item { PromoBanner() }

            item {

                val dummyProducts = listOf(
                    Product(1, "Women Printed Kurta", 1500.0, ""),
                    Product(2, "HRX by Hrithik Roshan", 2499.0, ""),
                    Product(3, "IWC Schaffhausen Watch", 650.0, "")
                )
                ProductsRow(
                    title = "Deal of the Day",
                    products = dummyProducts,
                    onViewAllClicked = {}
                )
            }



            item { FootwaresCard() }

            item {

                val trendingProducts = listOf(
                    Product(4, "Trending Watch", 1999.0, ""),
                    Product(5, "White Sneakers", 2999.0, ""),
                    Product(6, "Cool Handbag", 999.0, "")
                )
                ProductsRow(
                    title = "Trending Products",
                    products = trendingProducts,
                    onViewAllClicked = {}
                )
            }

       
        }

    }
}

