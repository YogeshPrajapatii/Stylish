package com.yogesh.stylish.data.repositoryimp.address

import com.yogesh.stylish.data.local.dao.AddressDao
import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.domain.repository.address.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao
) : AddressRepository {
    override fun getAddresses(): Flow<List<AddressEntity>> = addressDao.getAllAddresses()

    override suspend fun addAddress(address: AddressEntity) {
        addressDao.insertAddress(address)
    }

    override suspend fun removeAddress(address: AddressEntity) {
        addressDao.deleteAddress(address)
    }

    override suspend fun markAsDefault(addressId: Int) {
        addressDao.setDefaultAddress(addressId)
    }
}