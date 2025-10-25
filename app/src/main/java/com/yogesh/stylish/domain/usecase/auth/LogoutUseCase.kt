package com.yogesh.stylish.domain.usecase.auth

import com.yogesh.stylish.domain.repository.auth.AuthRepository
import javax.inject.Inject
import com.yogesh.stylish.domain.util.Result
class LogoutUseCase @Inject constructor(private val repository: AuthRepository){
    
    suspend operator fun invoke(): Result<String>{
        return repository.logout()
    }
    
}