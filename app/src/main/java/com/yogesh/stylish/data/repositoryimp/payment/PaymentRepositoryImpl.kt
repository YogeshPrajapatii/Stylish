package com.yogesh.stylish.data.repositoryimp.payment

import com.yogesh.stylish.data.local.dao.payment.PaymentDao
import com.yogesh.stylish.data.local.entity.payment.PaymentEntity
import com.yogesh.stylish.domain.repository.payment.PaymentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class PaymentRepositoryImpl(private val paymentDao: PaymentDao) : PaymentRepository {
    override suspend fun executePayment(amount: Int, orderId: Int): Result<PaymentEntity> {
        delay(3000)
        val isSuccess = true
        return if (isSuccess) {
            val payment = PaymentEntity(
                orderId = orderId,
                transactionId = "TXN_${UUID.randomUUID().toString().take(8).uppercase()}",
                amount = amount,
                paymentStatus = "SUCCESS"
            )
            paymentDao.insertPayment(payment)
            Result.success(payment)
        } else {
            Result.failure(Exception("Payment Failed"))
        }
    }

    override fun getPaymentStatus(orderId: Int): Flow<PaymentEntity?> = paymentDao.getPaymentByOrderId(orderId)
}