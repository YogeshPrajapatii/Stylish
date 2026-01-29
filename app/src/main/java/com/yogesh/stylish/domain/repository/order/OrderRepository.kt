package com.yogesh.stylish.domain.repository.order

import com.yogesh.stylish.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun placeOrder(order: OrderEntity)
    fun getAllOrders(): Flow<List<OrderEntity>>
}