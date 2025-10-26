package com.yogesh.stylish.domain.usecase.wishlist

import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.wishlist.WishlistRepository 
import javax.inject.Inject

class AddToWishlistUseCase @Inject constructor(
    private val repository: WishlistRepository
) {
   
    suspend operator fun invoke(product: Product) {
        repository.addToWishlist(product)
    }
}