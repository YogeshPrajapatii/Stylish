package com.yogesh.stylish.data.repositoryimp.cart

import com.yogesh.stylish.data.local.dao.CartDao
import com.yogesh.stylish.data.local.entity.CartEntity
import com.yogesh.stylish.domain.model.CartItem
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().map { entities ->
            entities.map { entity ->
                CartItem(
                    product = Product(
                        id = entity.productId,
                        title = entity.title,
                        price = entity.price,
                        thumbnail = entity.thumbnail,
                        discountPercentage = entity.discountPercentage,
                        description = "",
                        category = "",
                        brand = "",
                        images = emptyList(),
                        rating = 0.0,
                        stock = 0,
                        sizes = emptyList()
                    ),
                    quantity = entity.quantity,
                    selectedSize = entity.selectedSize
                )
            }
        }
    }

    override suspend fun addToCart(product: Product, quantity: Int, size: String) {
        val existingItem = cartDao.getCartItem(product.id, size)

        if (existingItem != null) {
            val newQuantity = existingItem.quantity + quantity
            cartDao.updateQuantity(product.id, size, newQuantity)
        } else {
            cartDao.insertCartItem(
                CartEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    thumbnail = product.thumbnail,
                    quantity = quantity,
                    discountPercentage = product.discountPercentage,
                    selectedSize = size
                )
            )
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}