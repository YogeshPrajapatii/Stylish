package com.yogesh.stylish.domain.usecase.wishlist

import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWishlistProductsUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getWishlistProducts()
    }
}