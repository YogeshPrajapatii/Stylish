package com.yogesh.stylish.presentation.feature.home.screen

import android.util.Log // Log के लिए इंपोर्ट जोड़ें
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
    val isLoading: Boolean = true, // Default to true initially
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

    private var isProductsLoaded: Boolean = false
    private var isCategoriesLoaded: Boolean = false

    init {
        // ViewModel की शुरुआत में दोनों को true पर रीसेट करें और डेटा लोड करें
        _state.update { it.copy(isLoading = true, error = null) }
        isProductsLoaded = false
        isCategoriesLoaded = false
        getProducts()
        getCategories()
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = getProductsUseCase()) {
                is Result.Success -> {
                    isProductsLoaded = true // Products सफलतापूर्वक लोड हुए
                    _state.update { currentState ->
                        currentState.copy(
                            products = result.data,
                            isLoading = !(isProductsLoaded && isCategoriesLoaded), // दोनों लोड होने पर ही false करें
                            error = null
                        )
                    }
                    Log.d("HomeViewModel", "getProducts: Products fetched. isLoading: ${_state.value.isLoading}")
                }

                is Result.Failure -> {
                    // एक भी API फेल होने पर isLoading को false करें और error दिखाएँ
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = result.message,
                            products = emptyList() // एरर होने पर प्रोडक्ट्स को खाली करें
                        )
                    }
                    Log.e("HomeViewModel", "getProducts: Failed to fetch products: ${result.message}")
                }
                Result.Loading -> {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
                Result.Ideal -> { /* Nothing to do */ }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase()) {
                is Result.Success -> {
                    isCategoriesLoaded = true // Categories सफलतापूर्वक लोड हुए
                    val categoryObjects = result.data.map { categoryDto ->
                        Category(name = categoryDto.name, imageUrl = getImageUrlForCategory(categoryDto.name.lowercase()))
                    }
                    _state.update { currentState ->
                        currentState.copy(
                            categories = categoryObjects,
                            isLoading = !(isProductsLoaded && isCategoriesLoaded), // दोनों लोड होने पर ही false करें
                            error = null
                        )
                    }
                    Log.d("HomeViewModel", "getCategories: Categories fetched. isLoading: ${_state.value.isLoading}")
                }

                is Result.Failure -> {
                    // एक भी API फेल होने पर isLoading को false करें और error दिखाएँ
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = result.message,
                            categories = emptyList() // एरर होने पर कैटेगरीज को खाली करें
                        )
                    }
                    Log.e("HomeViewModel", "getCategories: Failed to fetch categories: ${result.message}")
                }
                Result.Loading -> {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
                Result.Ideal -> { /* Nothing to do */ }
            }
        }
    }

    private fun getImageUrlForCategory(categoryName: String): String {
        return when (categoryName.lowercase()) {
            "beauty" -> "https://picsum.photos/id/1/200"
            "fragrances" -> "https://picsum.photos/id/9/200"
            "furniture" -> "https://picsum.photos/id/21/200"
            "groceries" -> "https://picsum.photos/id/25/200"
            "home-decoration" -> "https://picsum.photos/id/20/200"
            "kitchen-accessories" -> "https://picsum.photos/id/30/200"
            "laptops" -> "https://picsum.photos/id/2/200"
            "mens-shirts" -> "https://picsum.photos/id/45/200"
            "mens-shoes" -> "https://picsum.photos/id/48/200"
            "mens-watches" -> "https://picsum.photos/id/51/200"
            "mobile-accessories" -> "https://picsum.photos/id/55/200"
            "motorcycle" -> "https://picsum.photos/id/61/200"
            "skin-care" -> "https://picsum.photos/id/12/200"
            "smartphones" -> "https://picsum.photos/id/4/200"
            "sports-accessories" -> "https://picsum.photos/id/66/200"
            "sunglasses" -> "https://picsum.photos/id/71/200"
            "tablets" -> "https://picsum.photos/id/75/200"
            "tops" -> "https://picsum.photos/id/31/200"
            "vehicle" -> "https://picsum.photos/id/81/200"
            "womens-bags" -> "https://picsum.photos/id/85/200"
            "womens-dresses" -> "https://picsum.photos/id/35/200"
            "womens-jewellery" -> "https://picsum.photos/id/90/200"
            "womens-shoes" -> "https://picsum.photos/id/41/200"
            "womens-watches" -> "https://picsum.photos/id/95/200"
            else -> "https://picsum.photos/id/50/200"
        }
    }
}