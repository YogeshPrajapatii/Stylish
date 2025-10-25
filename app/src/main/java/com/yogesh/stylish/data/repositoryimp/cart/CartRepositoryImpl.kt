package com.yogesh.stylish.data.repositoryimp.cart

import com.yogesh.stylish.data.local.data_store.cart.CartDataStore
import com.yogesh.stylish.domain.model.CartItem
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CartRepositoryImpl(private val cartDataStore: CartDataStore
) : CartRepository { 

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDataStore.cartItems
    }

    override suspend fun addToCart(product: Product, quantity: Int) {
        val currentItems = cartDataStore.cartItems.first() 
        val mutableItems = currentItems.toMutableList()

        val existingItemIndex = mutableItems.indexOfFirst { it.product.id == product.id }

        if (existingItemIndex != -1) {
            val existingItem = mutableItems[existingItemIndex]
            mutableItems[existingItemIndex] =
                existingItem.copy(quantity = existingItem.quantity + quantity)
        } else {
            mutableItems.add(CartItem(product = product, quantity = quantity))
        }

        cartDataStore.updateCartItems(mutableItems)
    }

    override suspend fun removeFromCart(productId: Int) {
        val currentItems = cartDataStore.cartItems.first()
        val updatedItems = currentItems.filter { it.product.id != productId }
        cartDataStore.updateCartItems(updatedItems)
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        val currentItems = cartDataStore.cartItems.first()
        val updatedItems = currentItems.mapNotNull { item -> 
            if (item.product.id == productId) {
                if (quantity > 0) {
                    item.copy(quantity = quantity) 
                } else {
                    null
                }
            } else {
                item 
            }
        }
        cartDataStore.updateCartItems(updatedItems)
    }

    override suspend fun clearCart() {
        cartDataStore.clearCart() 
    }
}