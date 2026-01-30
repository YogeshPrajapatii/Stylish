package com.yogesh.stylish.presentation.feature.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.data.local.entity.OrderEntity
import com.yogesh.stylish.domain.model.CartItem
import com.yogesh.stylish.domain.repository.address.AddressRepository
import com.yogesh.stylish.domain.usecase.cart.GetCartItemsUseCase
import com.yogesh.stylish.domain.usecase.order.PlaceOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

data class CheckoutState(
    val cartItems: List<CartItem> = emptyList(),
    val selectedAddress: AddressEntity? = null,
    val totalOriginalPrice: Int = 0,
    val totalFinalPrice: Int = 0,
    val totalSavings: Int = 0,
    val isLoading: Boolean = false
)

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addressRepository: AddressRepository,
    private val placeOrderUseCase: PlaceOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CheckoutState())
    val state = _state.asStateFlow()

    init {
        loadCheckoutData()
    }

    private fun loadCheckoutData() {
        viewModelScope.launch {
            combine(
                getCartItemsUseCase(),
                addressRepository.getAddresses()
            ) { items, addresses ->
                val final = items.sumOf { (it.product.price * 90 * it.quantity).roundToInt() }
                val original = items.sumOf {
                    val discFactor = 1 - (it.product.discountPercentage / 100)
                    val origPrice = it.product.price / if (discFactor <= 0) 1.0 else discFactor
                    (origPrice * 90 * it.quantity).roundToInt()
                }

                _state.update { it.copy(
                    cartItems = items,
                    selectedAddress = addresses.find { it.isDefault } ?: addresses.firstOrNull(),
                    totalOriginalPrice = original,
                    totalFinalPrice = final,
                    totalSavings = original - final
                ) }
            }.collect()
        }
    }

    fun prepareOrder(onOrderCreated: (Int, Int) -> Unit) {
        viewModelScope.launch {
            val orderId = (100000..999999).random()
            val newOrder = OrderEntity(
                orderId = orderId,
                totalAmount = _state.value.totalFinalPrice,
                status = "PENDING",
                orderDate = System.currentTimeMillis(),
                itemCount = _state.value.cartItems.size,
                addressId = _state.value.selectedAddress?.id ?: 0
            )
            placeOrderUseCase(newOrder)
            onOrderCreated(_state.value.totalFinalPrice, orderId)
        }
    }
}