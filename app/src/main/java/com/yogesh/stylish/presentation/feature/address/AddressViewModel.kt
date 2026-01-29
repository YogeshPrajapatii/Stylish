package com.yogesh.stylish.presentation.feature.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.domain.usecase.address.AddAddressUseCase
import com.yogesh.stylish.domain.usecase.address.DeleteAddressUseCase
import com.yogesh.stylish.domain.usecase.address.GetAddressesUseCase
import com.yogesh.stylish.domain.usecase.address.SetDefaultAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val setDefaultAddressUseCase: SetDefaultAddressUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddressState())
    val state: StateFlow<AddressState> = _state.asStateFlow()

    init {
        loadAddresses()
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            getAddressesUseCase()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e -> _state.update { it.copy(isLoading = false, error = e.localizedMessage) } }
                .collect { addressList ->
                    _state.update { it.copy(isLoading = false, addresses = addressList) }
                }
        }
    }

    fun addAddress(address: AddressEntity) {
        viewModelScope.launch {
            try {
                addAddressUseCase(address)
                _state.update { it.copy(isAddressAdded = true) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.localizedMessage) }
            }
        }
    }

    fun deleteAddress(address: AddressEntity) {
        viewModelScope.launch {
            deleteAddressUseCase.invoke(address)
        }
    }

    fun setDefaultAddress(addressId: Int) {
        viewModelScope.launch {
            setDefaultAddressUseCase(addressId)
        }
    }

    fun resetAddAddressStatus() {
        _state.update { it.copy(isAddressAdded = false) }
    }
}