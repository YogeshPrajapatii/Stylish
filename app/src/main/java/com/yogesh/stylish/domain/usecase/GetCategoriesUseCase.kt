package com.yogesh.stylish.domain.usecase

import com.yogesh.stylish.domain.repository.ProductRepository
import javax.inject.Inject
import com.yogesh.stylish.domain.util.Result

class GetCategoriesUseCase @Inject constructor(private val repository: ProductRepository){
    
    suspend operator fun invoke(): Result<List<String>>{
        return repository.getAllCategories()
    }
    
}
