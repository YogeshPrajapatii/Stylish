package com.yogesh.stylish.presentation.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.model.Category
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.usecase.product.GetProductsUseCase
import com.yogesh.stylish.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val filteredProducts: List<Product> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getProductsUseCase()) {
                is Result.Success -> {
                    val products = result.data
                    val dynamicCategories = products.groupBy { it.category }
                        .map { (name, prodList) ->
                            Category(
                                name = name,
                                imageUrl = prodList.firstOrNull()?.thumbnail ?: ""
                            )
                        }

                    _state.update { it.copy(
                        products = products,
                        filteredProducts = products,
                        categories = dynamicCategories,
                        isLoading = false
                    ) }
                }
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> Unit
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        val filtered = if (query.isEmpty()) _state.value.products
        else _state.value.products.filter { it.title.contains(query, ignoreCase = true) }
        _state.update { it.copy(filteredProducts = filtered) }
    }
}