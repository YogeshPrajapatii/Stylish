package com.yogesh.stylish.domain.usecase.order

import com.yogesh.stylish.domain.repository.order.OrderRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderId: Int, status: String) {
        repository.updateOrderStatus(orderId, status)
    }
}