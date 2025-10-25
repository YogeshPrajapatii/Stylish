package com.yogesh.stylish.domain.repository.wishlist

import com.yogesh.stylish.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {

    suspend fun addToWishlist(product: Product)

    suspend fun removeFromWishlist(productId: Int)

    fun getWishlistProducts(): Flow<List<Product>>

    suspend fun isInWishlist(productId: Int): Boolean

    suspend fun clearWishlist()

}