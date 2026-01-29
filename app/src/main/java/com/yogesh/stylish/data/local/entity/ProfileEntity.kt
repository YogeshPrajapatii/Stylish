package com.yogesh.stylish.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_info")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val imageUri: String? = null,
    val email: String = "",
    val fullName: String = "",
    val pincode: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "India",
    val bankAccountNumber: String = "",
    val accountHolderName: String = "",
    val ifscCode: String = ""
)