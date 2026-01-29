package com.yogesh.stylish.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yogesh.stylish.domain.util.Constants
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

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
    val sizes: List<String>? = emptyList()
)

// Conversion -> USD to INR
val Product.finalPriceINR: Int
    get() {
        val discountedPriceUSD = this.price * (1 - this.discountPercentage / 100.0)
        return (discountedPriceUSD * Constants.USD_TO_INR).roundToInt()
    }

val Product.originalPriceINR: Int
    get() = (this.price * Constants.USD_TO_INR).roundToInt()