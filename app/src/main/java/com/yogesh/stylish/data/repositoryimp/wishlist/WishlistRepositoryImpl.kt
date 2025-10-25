package com.yogesh.stylish.data.repositoryimp.wishlist

import com.yogesh.stylish.data.local.dao.WishlistDao
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository
import kotlinx.coroutines.flow.Flow

class WishlistRepositoryImpl(
    private val wishlistDao: WishlistDao
) : WishlistRepository {

    override suspend fun addToWishlist(product: Product) {
        wishlistDao.insertWishlistItem(product) 
    }

    override suspend fun removeFromWishlist(productId: Int) {
        wishlistDao.deleteWishlistItem(productId) 
    }

    override fun getWishlistProducts(): Flow<List<Product>> {
        return wishlistDao.getAllWishlistItems()
    }

    override suspend fun isInWishlist(productId: Int): Boolean {
        return wishlistDao.isInWishlist(productId)
    }

    override suspend fun clearWishlist() {
        wishlistDao.clearWishlist() 
    }
}