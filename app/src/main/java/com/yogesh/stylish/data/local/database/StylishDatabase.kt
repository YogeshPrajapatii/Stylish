package com.yogesh.stylish.data.local.database 

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yogesh.stylish.data.local.converter.StringListConverter
import com.yogesh.stylish.data.local.dao.WishlistDao
import com.yogesh.stylish.domain.model.Product

@Database(
    entities = [Product::class], 
    version = 1,                
    exportSchema = false        )
@TypeConverters(StringListConverter::class) 
abstract class StylishDatabase : RoomDatabase() {

    abstract fun wishlistDao(): WishlistDao
}