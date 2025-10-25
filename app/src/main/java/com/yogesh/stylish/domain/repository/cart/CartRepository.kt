package com.yogesh.stylish.domain.repository.cart

import com.yogesh.stylish.domain.model.CartItem
import com.yogesh.stylish.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getCartItems(): Flow<List<CartItem>>

    suspend fun addToCart(product: Product, quantity: Int = 1)

    suspend fun removeFromCart(productId: Int)

    suspend fun updateQuantity(productId: Int, quantity: Int)

    suspend fun clearCart()
}