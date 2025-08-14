package com.yogesh.stylish.domain.repository

import com.yogesh.stylish.domain.model.Product

interface ProductRepository {
    
    suspend fun getAllProducts():Result<List<Product>>
    suspend fun getAllCategories(): Result<List<String>>
    suspend fun getProductById(id : Int) :Result<Product>
}