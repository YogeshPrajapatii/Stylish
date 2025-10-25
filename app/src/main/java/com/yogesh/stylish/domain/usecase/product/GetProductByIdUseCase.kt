package com.yogesh.stylish.domain.usecase.product

import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.product.ProductRepository
import com.yogesh.stylish.domain.util.Result
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(id: Int): Result<Product> {

        return repository.getProductById(id)

    }
}