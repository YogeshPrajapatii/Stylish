package com.yogesh.stylish.data.repositoryimp.order

import com.yogesh.stylish.data.local.dao.OrderDao
import com.yogesh.stylish.data.local.entity.OrderEntity
import com.yogesh.stylish.domain.repository.order.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(private val orderDao: OrderDao) : OrderRepository {
    override suspend fun placeOrder(order: OrderEntity) {
        orderDao.insertOrder(order)
    }

    override fun getAllOrders(): Flow<List<OrderEntity>> {
        return orderDao.getAllOrders()
    }
}