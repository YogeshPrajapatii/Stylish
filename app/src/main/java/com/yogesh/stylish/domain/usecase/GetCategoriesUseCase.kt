package com.yogesh.stylish.domain.usecase

import com.yogesh.stylish.data.remote.dto.CategoryDto
import com.yogesh.stylish.domain.repository.ProductRepository
import com.yogesh.stylish.domain.util.Result
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke(): Result<List<CategoryDto>> {
        return repository.getAllCategories()
    }

}
