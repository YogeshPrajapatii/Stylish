package com.yogesh.stylish.presentation.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yogesh.stylish.domain.repository.cart.CartRepository
import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository
import com.yogesh.stylish.domain.usecase.product.GetProductByIdUseCase
import com.yogesh.stylish.domain.util.Result
import com.yogesh.stylish.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val getProductByIdUseCase: GetProductByIdUseCase,
                                                 private val wishlistRepository: WishlistRepository,
                                                 private val cartRepository: CartRepository,
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
                                  checkWishlistStatus(id)
                    _state.update {
                        it.copy(product = result.data, isLoading = false, error = null)
                    }
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
                val isInWishlistNow = wishlistRepository.isInWishlist(productId)
                _state.update { it.copy(isInWishlist = isInWishlistNow) }
            } catch (e: Exception) {
                _state.update { it.copy(isInWishlist = false) }
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
                        wishlistRepository.removeFromWishlist(product.id)
                    } else {
                        wishlistRepository.addToWishlist(product)
                    }
                    checkWishlistStatus(product.id)
                } catch (e: Exception) {
                    _state.update { it.copy(error = "Wishlist update failed: ${e.message}") }
                }
            }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val product = _state.value.product
            if (product != null) {
                try {
                    cartRepository.addToCart(product, 1)
                } catch (e: Exception) {
                    _state.update { it.copy(error = "Add to cart failed: ${e.message}") }
                }
            }
        }
    }
}