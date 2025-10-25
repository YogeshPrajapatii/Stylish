package com.yogesh.stylish.data.repositoryimp.product

import com.yogesh.stylish.data.remote.ProductApiService
import com.yogesh.stylish.data.remote.dto.CategoryDto
import com.yogesh.stylish.data.remote.dto.ProductDto
import com.yogesh.stylish.domain.model.Category
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.product.ProductRepository
import com.yogesh.stylish.domain.util.Result

class ProductRepositoryImpl(private val apiService: ProductApiService
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

    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val categoryDto = apiService.getAllCategories()
            val categories = categoryDto.map { it.toDomainCategory() }
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
    return Product(id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        thumbnail = this.thumbnail,
        stock = this.stock,
        brand = this.brand ?: "Unknown",
        category = this.category,
        images = this.images,
        sizes = null)
}

private fun CategoryDto.toDomainCategory(): Category {
    return Category(name = this.name, imageUrl = this.url)
}