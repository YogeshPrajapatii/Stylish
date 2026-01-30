package com.yogesh.stylish.presentation.feature.address

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.domain.usecase.address.AddAddressUseCase
import com.yogesh.stylish.domain.usecase.address.DeleteAddressUseCase
import com.yogesh.stylish.domain.usecase.address.GetAddressesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addAddressUseCase: AddAddressUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddressState())
    val state = _state.asStateFlow()

    var fullName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var houseNumber by mutableStateOf("")
    var area by mutableStateOf("")
    var city by mutableStateOf("")
    var stateName by mutableStateOf("")
    var pincode by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    init {
        loadAddresses()
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getAddressesUseCase().collect { addressList ->
                _state.update { it.copy(addresses = addressList, isLoading = false) }
            }
        }
    }

    fun deleteAddress(address: AddressEntity) {
        viewModelScope.launch {
            deleteAddressUseCase(address)
        }
    }

    fun saveAddress(onSuccess: () -> Unit) {
        errorMessage = when {
            fullName.isBlank() || !fullName.all { it.isLetter() || it.isWhitespace() } -> "Full Name must contain only letters"
            phoneNumber.length != 10 || !phoneNumber.all { it.isDigit() } -> "Enter a valid 10-digit phone number"
            pincode.length != 6 || !pincode.all { it.isDigit() } -> "Enter a valid 6-digit Pincode"
            houseNumber.isBlank() || area.isBlank() || city.isBlank() || stateName.isBlank() -> "All fields are mandatory"
            else -> null
        }

        if (errorMessage == null) {
            viewModelScope.launch {
                val address = AddressEntity(
                    fullName = fullName,
                    phoneNumber = phoneNumber,
                    houseNumber = houseNumber,
                    area = area,
                    city = city,
                    state = stateName,
                    pincode = pincode,
                    isDefault = _state.value.addresses.isEmpty()
                )
                addAddressUseCase(address)
                onSuccess()
            }
        }
    }
}