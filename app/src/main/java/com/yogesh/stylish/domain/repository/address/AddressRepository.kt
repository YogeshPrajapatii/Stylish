package com.yogesh.stylish.domain.repository.address

import com.yogesh.stylish.data.local.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    fun getAddresses(): Flow<List<AddressEntity>>
    suspend fun addAddress(address: AddressEntity)
    suspend fun removeAddress(address: AddressEntity)
    suspend fun markAsDefault(addressId: Int)
}