package com.yogesh.stylish.data.local.dao.payment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogesh.stylish.data.local.entity.payment.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity)

    @Query("SELECT * FROM payments WHERE orderId = :orderId")
    fun getPaymentByOrderId(orderId: Int): Flow<PaymentEntity?>
}