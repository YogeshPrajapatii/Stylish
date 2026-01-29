package com.yogesh.stylish.domain.usecase.address

import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.domain.repository.address.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(): Flow<List<AddressEntity>> {
        return repository.getAddresses()
    }
}