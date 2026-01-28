package com.yogesh.stylish.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.*
import com.yogesh.stylish.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table WHERE productId = :productId AND selectedSize = :size")
    suspend fun getCartItem(productId: Int, size: String): CartEntity?

    @Query("SELECT * FROM cart_table")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Query("DELETE FROM cart_table WHERE productId = :productId AND selectedSize = :size")
    suspend fun deleteCartItem(productId: Int, size: String)

    @Query("UPDATE cart_table SET quantity = :quantity WHERE productId = :productId AND selectedSize = :size")
    suspend fun updateQuantity(productId: Int, size: String, quantity: Int)

    @Query("DELETE FROM cart_table")
    suspend fun clearCart()
}