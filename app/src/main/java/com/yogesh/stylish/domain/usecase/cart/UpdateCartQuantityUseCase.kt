package com.yogesh.stylish.domain.usecase.cart

import com.yogesh.stylish.domain.repository.cart.CartRepository
import javax.inject.Inject

class UpdateCartQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: Int, size: String, quantity: Int) {
        repository.updateQuantity(productId, size, quantity)
    }
}