package com.yogesh.stylish.domain.usecase.wishlist // Adjust package if needed

import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository
import javax.inject.Inject

class ClearWishlistUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke() {
        repository.clearWishlist()
    }
}