package com.yogesh.stylish.presentation.feature.home.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.model.Category
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.usecase.product.GetCategoriesUseCase
import com.yogesh.stylish.domain.usecase.product.GetProductsUseCase
import com.yogesh.stylish.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private var isProductsLoaded: Boolean = false
    private var isCategoriesLoaded: Boolean = false

    init {
        _state.update { it.copy(isLoading = true, error = null) }
        getProducts()
        getCategories()
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        if (query.isEmpty()) {
            _state.update { it.copy(filteredProducts = _state.value.products) }
        } else {
            val filtered = _state.value.products.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            _state.update { it.copy(filteredProducts = filtered) }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = getProductsUseCase()) {
                is Result.Success -> {
                    isProductsLoaded = true
                    _state.update { currentState ->
                        currentState.copy(
                            products = result.data,
                            filteredProducts = result.data,
                            isLoading = !(isProductsLoaded && isCategoriesLoaded),
                            error = null
                        )
                    }
                }
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
                Result.Loading -> _state.update { it.copy(isLoading = true) }
                else -> Unit
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase()) {
                is Result.Success -> {
                    isCategoriesLoaded = true
                    val categoryObjects = result.data.map { categoryDto ->
                        Category(name = categoryDto.name, imageUrl = "https://picsum.photos/id/50/200")
                    }
                    _state.update { currentState ->
                        currentState.copy(
                            categories = categoryObjects,
                            isLoading = !(isProductsLoaded && isCategoriesLoaded),
                            error = null
                        )
                    }
                }
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> Unit
            }
        }
    }
}