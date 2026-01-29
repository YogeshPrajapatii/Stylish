package com.yogesh.stylish.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yogesh.stylish.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>
}