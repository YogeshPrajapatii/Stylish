package com.yogesh.stylish.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yogesh.stylish.data.local.converter.StringListConverter
import com.yogesh.stylish.data.local.dao.AddressDao
import com.yogesh.stylish.data.local.dao.WishlistDao
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.data.local.entity.AddressEntity


import com.yogesh.stylish.data.local.dao.CartDao
import com.yogesh.stylish.data.local.dao.OrderDao
import com.yogesh.stylish.data.local.dao.ProfileDao
import com.yogesh.stylish.data.local.entity.CartEntity
import com.yogesh.stylish.data.local.entity.OrderEntity
import com.yogesh.stylish.data.local.entity.ProfileEntity

@Database(
    entities = [Product::class, CartEntity::class, AddressEntity::class, OrderEntity::class,ProfileEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class StylishDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
    abstract fun cartDao(): CartDao
    abstract fun addressDao(): AddressDao

    abstract fun orderDao(): OrderDao

    abstract fun profileDao(): ProfileDao
}