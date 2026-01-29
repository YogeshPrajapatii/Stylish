package com.yogesh.stylish.domain.usecase.address

import com.yogesh.stylish.domain.repository.address.AddressRepository
import javax.inject.Inject

class SetDefaultAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressId: Int) {
        repository.markAsDefault(addressId)
    }
}