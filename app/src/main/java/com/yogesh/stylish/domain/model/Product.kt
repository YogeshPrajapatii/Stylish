package com.yogesh.stylish.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.serialization.Serializable 

@Serializable
@Entity(tableName = "wishlist_table")
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
    val sizes: List<String>? = null 
)