package com.yogesh.stylish.domain.usecase.cart

import com.yogesh.stylish.domain.repository.cart.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int, size: String) {
        repository.removeFromCart(productId, size)
    }
}