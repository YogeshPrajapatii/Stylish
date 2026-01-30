package com.yogesh.stylish.presentation.feature.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.usecase.cart.ClearCartUseCase
import com.yogesh.stylish.domain.usecase.order.UpdateOrderStatusUseCase
import com.yogesh.stylish.domain.usecase.payment.ProcessPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentUiState(
    val isProcessing: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val processPaymentUseCase: ProcessPaymentUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PaymentUiState())
    val state = _state.asStateFlow()

    fun startPayment(amount: Int, orderId: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isProcessing = true) }
            val result = processPaymentUseCase(amount, orderId)
            if (result.isSuccess) {
                updateOrderStatusUseCase(orderId, "SUCCESS")
                clearCartUseCase()
                _state.update { it.copy(isProcessing = false, isSuccess = true) }
                onComplete()
            } else {
                updateOrderStatusUseCase(orderId, "FAILED")
                _state.update { it.copy(isProcessing = false, error = "Payment Failed") }
            }
        }
    }
}