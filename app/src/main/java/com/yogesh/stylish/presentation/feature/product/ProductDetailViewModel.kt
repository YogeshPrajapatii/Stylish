package com.yogesh.stylish.presentation.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yogesh.stylish.domain.usecase.cart.AddToCartUseCase
import com.yogesh.stylish.domain.usecase.product.GetProductByIdUseCase
import com.yogesh.stylish.domain.usecase.wishlist.AddToWishlistUseCase
import com.yogesh.stylish.domain.usecase.wishlist.CheckWishlistStatusUseCase
import com.yogesh.stylish.domain.usecase.wishlist.RemoveFromWishlistUseCase
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase,
    private val checkWishlistStatusUseCase: CheckWishlistStatusUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()
    private var currentProductId: Int = -1

    init {
        val args: Routes.ProductDetailScreen = savedStateHandle.toRoute()
        currentProductId = args.productId

        if (currentProductId != -1) {
            getProductById(currentProductId)
        } else {
            _state.update { it.copy(isLoading = false, error = "Invalid Product ID.") }
        }
    }

    private fun getProductById(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = getProductByIdUseCase(id)) {
                is Result.Failure -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
                is Result.Success -> {
                    _state.update {
                        it.copy(product = result.data, isLoading = false, error = null)
                    }
                    checkWishlistStatus(id)
                }
                else -> {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun checkWishlistStatus(productId: Int) {
        viewModelScope.launch {
            try {
                val isInWishlistNow = checkWishlistStatusUseCase(productId)
                _state.update { it.copy(isInWishlist = isInWishlistNow) }
            } catch (e: Exception) {
                _state.update { it.copy(isInWishlist = false, error = "Failed to check wishlist status") }
            }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            val currentState = _state.value
            val product = currentState.product
            if (product != null) {
                try {
                    if (currentState.isInWishlist) {
                        removeFromWishlistUseCase(product.id)
                    } else {
                        addToWishlistUseCase(product)
                    }
                    _state.update { it.copy(isInWishlist = !currentState.isInWishlist) }
                } catch (e: Exception) {
                    _state.update { it.copy(error = "Wishlist update failed: ${e.message}") }
                }
            }
        }
    }

    fun onSizeSelected(size: String) {
        _state.update { it.copy(selectedSize = size, error = null) }
    }

    fun addToCart() {
        viewModelScope.launch {
            val product = _state.value.product ?: return@launch
            val selectedSize = _state.value.selectedSize
            val isSizeRequired = !product.sizes.isNullOrEmpty()

            if (isSizeRequired && selectedSize.isEmpty()) {
                _state.update { it.copy(error = "Please select a size") }
                return@launch
            }

            try {
                val finalSize = if (isSizeRequired) selectedSize else ""
                addToCartUseCase(product, 1, finalSize)
                _state.update {
                    it.copy(
                        error = null,
                        snackbarMessage = "Added to cart successfully!"
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Add to cart failed: ${e.message}") }
            }
        }
    }

    fun resetSnackbarMessage() {
        _state.update { it.copy(snackbarMessage = null) }
    }
}