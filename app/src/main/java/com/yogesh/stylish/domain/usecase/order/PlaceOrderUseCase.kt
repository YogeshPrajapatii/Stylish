package com.yogesh.stylish.domain.usecase.order

import com.yogesh.stylish.data.local.dao.CartDao
import com.yogesh.stylish.data.local.entity.OrderEntity
import com.yogesh.stylish.domain.repository.order.OrderRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val repository: OrderRepository,
    cartDao: CartDao
) {
    suspend operator fun invoke(order: OrderEntity) {
        repository.placeOrder(order)
    }
}