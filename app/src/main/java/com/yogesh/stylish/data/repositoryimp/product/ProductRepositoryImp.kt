package com.yogesh.stylish.data.repositoryimp.product

import com.yogesh.stylish.data.remote.ProductApiService
import com.yogesh.stylish.data.remote.dto.CategoryDto
import com.yogesh.stylish.data.remote.dto.ProductDto
import com.yogesh.stylish.domain.model.Category
import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.product.ProductRepository
import com.yogesh.stylish.domain.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val apiService: ProductApiService
) : ProductRepository {

    private var cachedProducts: List<Product>? = null

    override suspend fun getAllProducts(): Result<List<Product>> {
        cachedProducts?.let {
            return Result.Success(it)
        }
        return try {
            val response = apiService.getAllProducts()
            val products = response.products.map { it.toDomainProduct() }
            cachedProducts = products
            Result.Success(products)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val categoryDtos = apiService.getAllCategories()
            val categories = categoryDtos.map { it.toDomainCategory() }
            Result.Success(categories)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        cachedProducts?.find { it.id == id }?.let {
            return Result.Success(it)
        }
        return try {
            val productDto = apiService.getProductById(id)
            Result.Success(productDto.toDomainProduct())
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Unknown Error!")
        }
    }
}

private fun ProductDto.toDomainProduct(): Product {
    val mockSizes = when {
        this.category.contains("shoes", ignoreCase = true) -> listOf("7 UK", "8 UK", "9 UK", "10 UK")
        this.category.contains("clothing", ignoreCase = true) -> listOf("S", "M", "L", "XL")
        else -> emptyList()
    }

    return Product(
        id = this.id,
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
        sizes = mockSizes
    )
}

private fun CategoryDto.toDomainCategory(): Category {
    return Category(name = this.name, imageUrl = this.url)
}