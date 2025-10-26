package com.yogesh.stylish.domain.usecase.wishlist 

import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository 
import javax.inject.Inject

class CheckWishlistStatusUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
   
    suspend operator fun invoke(productId: Int): Boolean {
        return repository.isInWishlist(productId)
    }
}