package com.yogesh.stylish.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val phoneNumber: String,
    val houseNumber: String,
    val area: String,
    val city: String,
    val state: String,
    val pincode: String,
    val isDefault: Boolean = false
)