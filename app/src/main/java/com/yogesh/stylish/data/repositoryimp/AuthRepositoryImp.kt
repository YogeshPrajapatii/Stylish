package com.yogesh.stylish.data.repositoryimp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yogesh.stylish.domain.repository.AuthRepository
import com.yogesh.stylish.domain.util.Result
import kotlinx.coroutines.tasks.await


class AuthRepositoryImp : AuthRepository {
    override suspend fun login(email: String, password: String): Result<String> {

        return try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            Result.Success("Login Successful")
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Login failed")
        }
    }

    override suspend fun signup(email: String, password: String): Result<String> {
        return try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            Result.Success("Sign Up Successful")
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Sign up failed")
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }
}
