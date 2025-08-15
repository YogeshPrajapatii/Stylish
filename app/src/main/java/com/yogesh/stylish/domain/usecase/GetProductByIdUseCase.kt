package com.yogesh.stylish.domain.usecase

import com.yogesh.stylish.domain.model.Product
import com.yogesh.stylish.domain.repository.ProductRepository
import javax.inject.Inject
import com.yogesh.stylish.domain.util.Result

class GetProductByIdUseCase @Inject constructor(private val repository: ProductRepository){
    suspend operator fun invoke(id : Int): Result<Product> {
        
        return repository.getProductById(id)
        
    }
}