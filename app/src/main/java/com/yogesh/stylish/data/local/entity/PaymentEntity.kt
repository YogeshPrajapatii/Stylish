package com.yogesh.stylish.data.local.entity.payment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: Int,
    val transactionId: String,
    val amount: Int,
    val paymentStatus: String,
    val paymentDate: Long = System.currentTimeMillis()
)