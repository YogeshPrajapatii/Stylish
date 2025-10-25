package com.yogesh.stylish.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogesh.stylish.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) 
    suspend fun insertWishlistItem(item: Product)

    @Query("DELETE FROM wishlist_table WHERE id = :productId")
    suspend fun deleteWishlistItem(productId: Int)

    @Query("SELECT * FROM wishlist_table") 
    fun getAllWishlistItems(): Flow<List<Product>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_table WHERE id = :productId)")
    suspend fun isInWishlist(productId: Int): Boolean

    @Query("DELETE FROM wishlist_table") 
    suspend fun clearWishlist()
}