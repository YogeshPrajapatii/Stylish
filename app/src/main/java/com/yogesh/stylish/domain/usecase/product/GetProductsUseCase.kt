package com.yogesh.stylish.domain.usecase.product

import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.product.ProductRepository
import com.yogesh.stylish.domain.util.Result
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getAllProducts()
    }

}