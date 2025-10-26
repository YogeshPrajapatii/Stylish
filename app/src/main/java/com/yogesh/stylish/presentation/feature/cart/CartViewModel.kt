package com.yogesh.stylish.presentation.feature.cart 

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.model.CartItem
import com.yogesh.stylish.domain.model.Product 
import com.yogesh.stylish.domain.usecase.cart.AddToCartUseCase
import com.yogesh.stylish.domain.usecase.cart.ClearCartUseCase
import com.yogesh.stylish.domain.usecase.cart.GetCartItemsUseCase
import com.yogesh.stylish.domain.usecase.cart.RemoveFromCartUseCase
import com.yogesh.stylish.domain.usecase.cart.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel 
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase, 
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
        viewModelScope.launch {
            getCartItemsUseCase() 
                .onStart {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
                .catch { exception ->
                    _state.update {
                        it.copy(isLoading = false, error = exception.localizedMessage ?: "Failed to load cart")
                    }
                }
                .collect { items ->
                   
                    val calculatedTotalItems = items.sumOf { it.quantity }
                    val calculatedTotalPrice = items.sumOf {

                        val pricePerItem = it.product.price * (1 - it.product.discountPercentage / 100.0)
                        pricePerItem * it.quantity
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
                }
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                addToCartUseCase(product, quantity)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to add item") }
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            try {
                removeFromCartUseCase(productId)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage ?: "Failed to remove item") }
            }
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity < 0) {
            _state.update { it.copy(error = "Quantity cannot be negative") }
            return
        }

        viewModelScope.launch {
            try {
                updateCartQuantityUseCase(productId, quantity)
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