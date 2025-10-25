package com.yogesh.stylish.domain.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.yogesh.stylish.domain.util.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<String>
    suspend fun signup(email: String, password: String): Result<String>
    
    suspend fun logout(): Result<String>
    fun getCurrentUser(): FirebaseUser?
    
    

}