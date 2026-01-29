package com.yogesh.stylish.presentation.feature.cart

import com.yogesh.stylish.domain.model.CartItem

data class CartState(val isLoading: Boolean = false,
                     val cartItems: List<CartItem> = emptyList(),
                     val error: String? = null,
                     val totalItems: Int = 0,
                     val totalPrice: Double = 0.0 )


