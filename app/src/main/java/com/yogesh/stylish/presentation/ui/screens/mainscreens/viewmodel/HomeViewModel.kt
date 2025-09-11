package com.yogesh.stylish.presentation.ui.screens.mainscreens.viewmodel

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

data class HomeScreenState(val isLoading: Boolean = true,
                           val products: List<Product> = emptyList(),
                           val categories: List<Category> = emptyList(),
                           val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase,
                                        private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoading = true) }

        getProducts()
        getCategories()
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = getProductsUseCase()) {
                is Result.Success -> {
                    _state.update { currentState ->
                        currentState.copy(products = result.data,
                            isLoading = _state.value.categories.isEmpty())
                    }
                }

                is Result.Failure -> {
                    _state.update { currentState ->
                        currentState.copy(isLoading = false, error = result.message)
                    }
                }

                else -> {}
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            when (val result = getCategoriesUseCase()) {
                is Result.Success -> {
                    val categoryObjects = result.data.map { categoryDto ->

                        val categoryNameForCheck = categoryDto.name.lowercase()

                        Category(name = categoryDto.name,
                            imageUrl = getImageUrlForCategory(categoryNameForCheck))
                    }
                    _state.update { currentState ->
                        currentState.copy(categories = categoryObjects,
                            isLoading = _state.value.products.isEmpty())
                    }
                }

                is Result.Failure -> {
                    _state.update { currentState ->
                        currentState.copy(isLoading = false, error = result.message)
                    }
                }

                else -> {}
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
            else -> "https://picsum.photos/id/50/200" // Default image


        }
    }
}