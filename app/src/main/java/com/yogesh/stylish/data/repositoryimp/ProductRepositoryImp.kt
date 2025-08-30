package com.yogesh.stylish.data.repositoryimp

import com.yogesh.stylish.data.remote.ProductApiService
import com.yogesh.stylish.data.remote.dto.CategoryDto
import com.yogesh.stylish.data.remote.dto.ProductDto
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.ProductRepository
import com.yogesh.stylish.domain.util.Result

class ProductRepositoryImpl(
    private val apiService: ProductApiService
) : ProductRepository {

    override suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getAllProducts()
            val products = response.products.map { it.toDomainProduct() }
            Result.Success(products)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getAllCategories(): Result<List<CategoryDto>> {
        return try {
            val categories = apiService.getAllCategories()
            Result.Success(categories)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {

        return try {
            val productDto = apiService.getProductById(id)
            Result.Success(productDto.toDomainProduct())
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Unknown Error !")
        }


    }
}

private fun ProductDto.toDomainProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        thumbnail = this.thumbnail,
        stock = 0,
        brand = "",
        category = "",
        images = emptyList()
    )
}