package com.yogesh.stylish.domain.usecase.payment

import com.yogesh.stylish.data.local.entity.payment.PaymentEntity
import com.yogesh.stylish.domain.repository.payment.PaymentRepository
import javax.inject.Inject

class ProcessPaymentUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    suspend operator fun invoke(amount: Int, orderId: Int): Result<PaymentEntity> {
        return repository.executePayment(amount, orderId)
    }
}