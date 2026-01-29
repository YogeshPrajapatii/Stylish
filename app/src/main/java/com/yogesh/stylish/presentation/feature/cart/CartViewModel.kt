package com.yogesh.stylish.presentation.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.model.finalPriceINR
import com.yogesh.stylish.domain.usecase.cart.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        getCartItemsUseCase()
            .onStart { _state.update { it.copy(isLoading = true, error = null) } }
            .catch { exception ->
                _state.update { it.copy(isLoading = false, error = exception.localizedMessage ?: "Failed to load cart") }
            }
            .onEach { items ->
                val calculatedTotalItems = items.sumOf { it.quantity }
                val calculatedTotalPrice = items.sumOf { item ->
                    (item.product.finalPriceINR * item.quantity).toDouble()
                }
                _state.update {
                    it.copy(
                        isLoading = false,
                        cartItems = items,
                        totalItems = calculatedTotalItems,
                        totalPrice = calculatedTotalPrice,
                        error = null
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun removeFromCart(productId: Int, size: String) {
        viewModelScope.launch {
            try {
                removeFromCartUseCase(productId, size)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to remove item") }
            }
        }
    }

    fun updateQuantity(productId: Int, size: String, newQuantity: Int) {
        if (newQuantity < 1) {
            removeFromCart(productId, size)
            return
        }
        viewModelScope.launch {
            try {
                updateCartQuantityUseCase(productId, size, newQuantity)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to update quantity") }
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                clearCartUseCase()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to clear cart") }
            }
        }
    }
}