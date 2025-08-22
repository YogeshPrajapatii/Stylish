package com.yogesh.stylish.presentation.ui.screens.mainscreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.model.Category
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.usecase.GetCategoriesUseCase
import com.yogesh.stylish.domain.usecase.GetProductsUseCase
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
    val categories: List<Category> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        getProducts()
        getCategories()
    }

    private fun getProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = getProductsUseCase()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(products = result.data, error = null, isLoading = false)
                    }
                }
                is Result.Failure -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
                // Add this else block to handle Ideal and Loading
                else -> {
                    // We don't need to do anything for these states here
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase()) {
                is Result.Success -> {
                    val categoryObjects = result.data.map { categoryName ->
                        Category(
                            name = categoryName,
                            imageUrl = getImageUrlForCategory(categoryName)
                        )
                    }
                    _state.update {
                        it.copy(categories = categoryObjects, error = null)
                    }
                }
                is Result.Failure -> {
                    _state.update {
                        it.copy(error = result.message)
                    }
                }
                // Add this else block to handle Ideal and Loading
                else -> {
                    // We don't need to do anything for these states here
                }
            }
        }
    }

    private fun getImageUrlForCategory(categoryName: String): String {
        return when (categoryName) {
            "smartphones" -> "https://cdn.dummyjson.com/product-images/2/thumbnail.jpg"
            "laptops" -> "https://cdn.dummyjson.com/product-images/6/thumbnail.png"
            "fragrances" -> "https://cdn.dummyjson.com/product-images/11/thumbnail.jpg"
            "skincare" -> "https://cdn.dummyjson.com/product-images/16/thumbnail.jpg"
            "groceries" -> "https://cdn.dummyjson.com/product-images/21/thumbnail.png"
            "home-decoration" -> "https://cdn.dummyjson.com/product-images/26/thumbnail.jpg"
            else -> "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
        }
    }
}