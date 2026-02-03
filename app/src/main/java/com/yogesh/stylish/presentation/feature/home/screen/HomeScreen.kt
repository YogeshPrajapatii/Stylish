package com.yogesh.stylish.presentation.feature.home.screen

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.presentation.feature.home.components.*
import com.yogesh.stylish.presentation.feature.product.ProductCard
import com.yogesh.stylish.presentation.feature.product.ProductsRow
import com.yogesh.stylish.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    var showSortSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = { HomeAppBar(navController) },
        bottomBar = {
            MyBottomBar(
                navController = navController,
                onSearchClick = {
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            if (!state.isOnline) {
                NoInternetScreen(onRetry = { viewModel.fetchInitialData() })
            } else {
                SearchAndFilterSection(
                    query = state.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                    onSortClick = { showSortSheet = true },
                    onFilterClick = { showFilterSheet = true },
                    modifier = Modifier.focusRequester(focusRequester)
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

        if (showSortSheet) {
            ModalBottomSheet(onDismissRequest = { showSortSheet = false }) {
                SortOptionsContent(state.sortOrder) { viewModel.onSortOrderChanged(it); showSortSheet = false }
            }
        }

        if (showFilterSheet) {
            ModalBottomSheet(onDismissRequest = { showFilterSheet = false }) {
                FilterOptionsContent(state.minPrice, state.maxPrice, state.selectedRating) { min, max, rating ->
                    viewModel.onFilterApplied(min, max, rating)
                    showFilterSheet = false
                }
            }
        }
    }
}

@Composable
fun SearchSuggestionsGrid(state: HomeScreenState, navController: NavHostController) {
    if (state.filteredProducts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text("No products found for '${state.searchQuery}'", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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
    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                products = state.products.take(6),
                onViewAllClicked = {},
                subtitle = "Limited Time Offer",
                icon = Icons.Default.Schedule,
                headerContainerColor = MaterialTheme.colorScheme.secondary,
                headerContentColor = MaterialTheme.colorScheme.onSecondary,
                onProductClick = { navController.navigate(Routes.ProductDetailScreen(it)) }
            )
        }
        item { OfferCards() }
        item { FootwaresCard() }
        item {
            val trendingProducts = state.products.filter { it.rating > 4.5 }.take(6)
            ProductsRow(
                title = "Trending Products",
                products = trendingProducts,
                onViewAllClicked = {},
                subtitle = "Top Rated",
                icon = Icons.Default.DateRange,
                headerContainerColor = MaterialTheme.colorScheme.primary,
                headerContentColor = MaterialTheme.colorScheme.onPrimary,
                onProductClick = { navController.navigate(Routes.ProductDetailScreen(it)) }
            )
        }
        item { SummerSale() }
        item { SponsoredCard() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun NoInternetScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.WifiOff,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("No Internet Connection", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Please check your network settings and try again.", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
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