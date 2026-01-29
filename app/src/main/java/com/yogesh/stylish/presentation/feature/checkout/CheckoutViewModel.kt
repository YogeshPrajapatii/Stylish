package com.yogesh.stylish.presentation.feature.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.data.local.entity.OrderEntity
import com.yogesh.stylish.domain.model.CartItem
import com.yogesh.stylish.domain.model.finalPriceINR
import com.yogesh.stylish.domain.model.originalPriceINR
import com.yogesh.stylish.domain.repository.address.AddressRepository
import com.yogesh.stylish.domain.usecase.cart.GetCartItemsUseCase
import com.yogesh.stylish.domain.usecase.order.PlaceOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CheckoutState(
    val cartItems: List<CartItem> = emptyList(),
    val selectedAddress: AddressEntity? = null,
    val totalOriginalPrice: Int = 0,
    val totalDiscountedPrice: Int = 0,
    val totalSavings: Int = 0,
    val shippingCharge: Int = 0,
    val finalPayable: Int = 0,
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
        loadCheckoutDetails()
    }

    private fun loadCheckoutDetails() {
        viewModelScope.launch {
            addressRepository.getAddresses().collect { addresses ->
                val default = addresses.find { it.isDefault } ?: addresses.firstOrNull()
                _state.update { it.copy(selectedAddress = default) }
            }
        }

        getCartItemsUseCase().onEach { items ->
            val original = items.sumOf { it.product.originalPriceINR * it.quantity }
            val discounted = items.sumOf { it.product.finalPriceINR * it.quantity }
            val savings = original - discounted
            val shipping = if (discounted in 1..499) 40 else 0

            _state.update {
                it.copy(
                    cartItems = items,
                    totalOriginalPrice = original,
                    totalDiscountedPrice = discounted,
                    totalSavings = savings,
                    shippingCharge = shipping,
                    finalPayable = discounted + shipping
                )
            }
        }.launchIn(viewModelScope)
    }

    fun placeOrder(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val order = OrderEntity(
                totalAmount = _state.value.finalPayable,
                addressId = _state.value.selectedAddress?.id ?: 0,
                itemCount = _state.value.cartItems.size
            )
            placeOrderUseCase(order)
            onSuccess()
        }
    }
}