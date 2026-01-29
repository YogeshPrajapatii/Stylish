package com.yogesh.stylish.data.local.dao

import androidx.room.*
import com.yogesh.stylish.data.local.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Query("SELECT * FROM address_table")
    fun getAllAddresses(): Flow<List<AddressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressEntity)

    @Delete
    suspend fun deleteAddress(address: AddressEntity)

    @Query("UPDATE address_table SET isDefault = (id = :addressId)")
    suspend fun setDefaultAddress(addressId: Int)

    @Query("SELECT * FROM address_table WHERE isDefault = 1 LIMIT 1")
    suspend fun getDefaultAddress(): AddressEntity?
}