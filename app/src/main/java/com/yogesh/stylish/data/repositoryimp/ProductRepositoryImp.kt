package com.yogesh.stylish.data.repositoryimp

import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.ProductRepository

class ProductRepositoryImp: ProductRepository{
    override suspend fun getAllProducts(): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCategories(): Result<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        TODO("Not yet implemented")
    }

} 