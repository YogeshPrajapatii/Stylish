package com.yogesh.stylish.domain.usecase.address

import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.domain.repository.address.AddressRepository
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(address: AddressEntity) {
        repository.addAddress(address)
    }
}