package com.yogesh.stylish.domain.usecase.product

import com.yogesh.stylish.domain.model.Category
import com.yogesh.stylish.domain.repository.product.ProductRepository
import com.yogesh.stylish.domain.util.Result
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke(): Result<List<Category>> {
        return repository.getAllCategories()
    }

}