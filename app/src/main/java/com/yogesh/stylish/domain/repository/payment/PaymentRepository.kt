package com.yogesh.stylish.domain.repository.payment

import com.yogesh.stylish.data.local.entity.payment.PaymentEntity
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun executePayment(amount: Int, orderId: Int): Result<PaymentEntity>
    fun getPaymentStatus(orderId: Int): Flow<PaymentEntity?>
}