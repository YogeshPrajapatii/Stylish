package com.yogesh.stylish.presentation.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.presentation.navigation.Routes
import com.yogesh.stylish.presentation.feature.home.components.*
import com.yogesh.stylish.presentation.feature.product.ProductCard
import com.yogesh.stylish.presentation.feature.product.ProductsRow
import com.yogesh.stylish.presentation.theme.StylishRed
import com.yogesh.stylish.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    var showSortSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { HomeAppBar(navController) },
        bottomBar = { MyBottomBar(navController) },
        containerColor = Color.White
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color.White)) {

            if (!state.isOnline) {
                NoInternetScreen(onRetry = { viewModel.fetchInitialData() })
            } else {
                SearchAndFilterSection(
                    query = state.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                    onSortClick = { showSortSheet = true },
                    onFilterClick = { showFilterSheet = true }
                )

                if (state.isLoading) {
                    ShimmerEffect()
                } else if (state.error != null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.error!!)
                    }
                } else {
                    if (state.searchQuery.isNotEmpty() || state.sortOrder != SortOrder.NONE || state.selectedRating > 0f) {
                        SearchSuggestionsGrid(state, navController)
                    } else {
                        HomeContentFeed(state, navController)
                    }
                }
            }
        }

        // Bottom Sheets
        if (showSortSheet) {
            ModalBottomSheet(onDismissRequest = { showSortSheet = false }, containerColor = Color.White) {
                SortOptionsContent(state.sortOrder) { viewModel.onSortOrderChanged(it); showSortSheet = false }
            }
        }

        if (showFilterSheet) {
            ModalBottomSheet(onDismissRequest = { showFilterSheet = false }, containerColor = Color.White) {
                FilterOptionsContent(state.minPrice, state.maxPrice, state.selectedRating) { min, max, rating ->
                    viewModel.onFilterApplied(min, max, rating)
                    showFilterSheet = false
                }
            }
        }
    }
}

// --- Helper UI Components ---

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
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                onViewAllClicked = {},
                subtitle = "Limited Time Offer",
                icon = Icons.Default.Schedule,
                headerContainerColor = Color(0xFF4392F9),
                headerContentColor = Color.White,
                onProductClick = { navController.navigate(Routes.ProductDetailScreen(it)) }
            )
        }
        item { OfferCards() }
        item {
            val trendingProducts = state.products.filter { it.rating > 4.5 }
            ProductsRow(
                title = "Trending Products",
                products = trendingProducts,
                onViewAllClicked = {},
                subtitle = "Top Rated",
                icon = Icons.Default.DateRange,
                headerContainerColor = StylishRed,
                headerContentColor = White,
                onProductClick = { navController.navigate(Routes.ProductDetailScreen(it)) }
            )
        }
        item { SummerSaleCard() }
        item { SponsoredCard() }
        item { Spacer(modifier = Modifier.height(10.dp)) }
    }
}

@Composable
fun NoInternetScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.WifiOff, null, Modifier.size(100.dp), Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Text("No Internet Connection", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Please check your network settings and try again.", textAlign = TextAlign.Center, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRetry, Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
            Text("Retry")
        }
    }
}

@Composable
fun SortOptionsContent(selectedOrder: SortOrder, onOrderSelected: (SortOrder) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Sort By", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        SortItem("Price: Low to High", selectedOrder == SortOrder.PRICE_LOW_HIGH) { onOrderSelected(SortOrder.PRICE_LOW_HIGH) }
        SortItem("Price: High to Low", selectedOrder == SortOrder.PRICE_HIGH_LOW) { onOrderSelected(SortOrder.PRICE_HIGH_LOW) }
        SortItem("Customer Rating", selectedOrder == SortOrder.RATING) { onOrderSelected(SortOrder.RATING) }
        SortItem("Clear All", false) { onOrderSelected(SortOrder.NONE) }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SortItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(48.dp).clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun FilterOptionsContent(min: Float, max: Float, rating: Float, onApply: (Float, Float, Float) -> Unit) {
    var priceRange by remember { mutableStateOf(min..max) }
    var selectedRating by remember { mutableStateOf(rating) }
    Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        Text("Filters", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        Text("Price Range: ₹${priceRange.start.toInt()} - ₹${priceRange.endInclusive.toInt()}")
        RangeSlider(value = priceRange, onValueChange = { priceRange = it }, valueRange = 0f..5000f, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(24.dp))
        Text("Minimum Rating: ${selectedRating.toInt()}+ Stars")
        Slider(value = selectedRating, onValueChange = { selectedRating = it }, valueRange = 0f..5f, steps = 4)
        Spacer(Modifier.height(32.dp))
        Button(onClick = { onApply(priceRange.start, priceRange.endInclusive, selectedRating) }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)) {
            Text("Apply Filters")
        }
    }
}