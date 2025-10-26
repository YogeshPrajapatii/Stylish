package com.yogesh.stylish.presentation.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.usecase.auth.LoginUseCase
import com.yogesh.stylish.domain.usecase.auth.SignUpUseCase
import com.yogesh.stylish.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val loginUseCase: LoginUseCase,
                                        private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<String>>(Result.Ideal)
    val authState: StateFlow<Result<String>> = _authState

    fun login(email: String, password: String) {
        _authState.value = Result.Loading

        viewModelScope.launch {
            try {
                _authState.value = loginUseCase(email, password)
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.localizedMessage ?: "Login Failed!")
            }
        }
    }

    fun signup(email: String, password: String) {
        _authState.value = Result.Loading

        viewModelScope.launch {
            try {
                _authState.value = signUpUseCase(email, password)
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.localizedMessage ?: "Sign Up Failed!")
            }
        }
    }
}
