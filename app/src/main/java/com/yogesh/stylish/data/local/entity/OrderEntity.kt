package com.yogesh.stylish.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val orderDate: Long = System.currentTimeMillis(),
    val totalAmount: Int,
    val addressId: Int,
    val itemCount: Int,
    val status: String = "Placed"
)