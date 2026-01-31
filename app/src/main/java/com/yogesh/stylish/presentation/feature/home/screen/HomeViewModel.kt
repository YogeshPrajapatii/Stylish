package com.yogesh.stylish.presentation.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.model.Category
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.usecase.product.GetProductsUseCase
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.domain.manager.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

enum class SortOrder { NONE, PRICE_LOW_HIGH, PRICE_HIGH_LOW, RATING }

data class HomeScreenState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.NONE,
    val minPrice: Float = 0f,
    val maxPrice: Float = 5000f,
    val selectedRating: Float = 0f,
    val isOnline: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val networkManager: NetworkManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        observeNetwork()
        fetchInitialData()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkManager.isConnected.collect { online ->
                _state.update { it.copy(isOnline = online) }
                // अगर इंटरनेट वापस आए और डेटा खाली हो, तो ऑटो-लोड करें
                if (online && _state.value.products.isEmpty()) {
                    fetchInitialData()
                }
            }
        }
    }

    fun fetchInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getProductsUseCase()) {
                is Result.Success -> {
                    val products = result.data
                    val dynamicCategories = products.groupBy { it.category }
                        .map { (name, prodList) ->
                            Category(name = name, imageUrl = prodList.firstOrNull()?.thumbnail ?: "")
                        }
                    _state.update { it.copy(
                        products = products,
                        filteredProducts = products,
                        categories = dynamicCategories,
                        isLoading = false,
                        error = null
                    ) }
                }
                is Result.Failure -> _state.update { it.copy(isLoading = false, error = result.message) }
                else -> Unit
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        applyFiltersAndSort()
    }

    fun onSortOrderChanged(order: SortOrder) {
        _state.update { it.copy(sortOrder = order) }
        applyFiltersAndSort()
    }

    fun onFilterApplied(minPrice: Float, maxPrice: Float, rating: Float) {
        _state.update { it.copy(minPrice = minPrice, maxPrice = maxPrice, selectedRating = rating) }
        applyFiltersAndSort()
    }

    private fun applyFiltersAndSort() {
        val query = _state.value.searchQuery
        val minP = _state.value.minPrice
        val maxP = _state.value.maxPrice
        val rating = _state.value.selectedRating

        var list = _state.value.products

        if (query.isNotEmpty()) {
            list = list.filter { it.title.contains(query, ignoreCase = true) }
        }

        list = list.filter {
            val price = (it.price * 90).roundToInt()
            price >= minP && price <= maxP && it.rating >= rating
        }

        list = when (_state.value.sortOrder) {
            SortOrder.PRICE_LOW_HIGH -> list.sortedBy { it.price }
            SortOrder.PRICE_HIGH_LOW -> list.sortedByDescending { it.price }
            SortOrder.RATING -> list.sortedByDescending { it.rating }
            SortOrder.NONE -> list
        }

        _state.update { it.copy(filteredProducts = list) }
    }
}