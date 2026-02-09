package com.yogesh.stylish.presentation.feature.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.usecase.cart.ClearCartUseCase
import com.yogesh.stylish.domain.usecase.order.UpdateOrderStatusUseCase
import com.yogesh.stylish.domain.util.NotificationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentUiState(
    val isProcessing: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val currentOrderId: Int? = null
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val clearCartUseCase: ClearCartUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    private val _state = MutableStateFlow(PaymentUiState())
    val state = _state.asStateFlow()

    fun setOrderId(orderId: Int) {
        _state.update { it.copy(currentOrderId = orderId) }
    }

    fun handlePaymentResult(success: Boolean, message: String?) {
        val orderId = _state.value.currentOrderId ?: return
        viewModelScope.launch {
            if (success) {
                updateOrderStatusUseCase(orderId, "SUCCESS")
                notificationHelper.showOrderNotification(orderId)
                clearCartUseCase()
                _state.update { it.copy(isProcessing = false, isSuccess = true, error = null) }
            } else {
                updateOrderStatusUseCase(orderId, "FAILED")
                _state.update { it.copy(isProcessing = false, isSuccess = false, error = message ?: "Payment Failed") }
            }
        }
    }

    fun startProcessing() {
        _state.update { it.copy(isProcessing = true, error = null) }
    }

    fun resetState() {
        _state.update { PaymentUiState() }
    }
}