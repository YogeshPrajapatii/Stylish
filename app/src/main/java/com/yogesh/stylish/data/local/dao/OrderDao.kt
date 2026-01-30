package com.yogesh.stylish.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogesh.stylish.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getOrders(): Flow<List<OrderEntity>>

    @Query("UPDATE orders SET status = :newStatus WHERE orderId = :orderId")
    suspend fun updateOrderStatus(orderId: Int, newStatus: String)
}