package com.yogesh.stylish.domain.usecase.cart // Adjust package if needed

import com.yogesh.stylish.domain.model.CartItem
import com.yogesh.stylish.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
  
    operator fun invoke(): Flow<List<CartItem>> {
        return repository.getCartItems()
    }
}