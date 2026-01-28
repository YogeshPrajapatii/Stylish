package com.yogesh.stylish.domain.usecase.cart 

import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.cart.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    
    suspend operator fun invoke(product: Product, quantity: Int = 1, size: String) {
        repository.addToCart(product, quantity, size)
    }
}