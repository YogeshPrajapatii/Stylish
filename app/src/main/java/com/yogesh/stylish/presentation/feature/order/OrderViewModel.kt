package com.yogesh.stylish.presentation.feature.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.data.local.entity.OrderEntity
import com.yogesh.stylish.domain.repository.order.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OrderState(
    val orders: List<OrderEntity> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OrderState())
    val state = _state.asStateFlow()

    init {
        getOrders()
    }

    private fun getOrders() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            repository.getAllOrders().collectLatest { orderList ->
                _state.value = _state.value.copy(
                    orders = orderList,
                    isLoading = false
                )
            }
        }
    }
}