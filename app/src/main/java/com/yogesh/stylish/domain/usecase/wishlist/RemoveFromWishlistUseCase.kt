package com.yogesh.stylish.domain.usecase.wishlist

import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository
import javax.inject.Inject

class RemoveFromWishlistUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(productId: Int) {
        repository.removeFromWishlist(productId)
    }
}