package com.yogesh.stylish.presentation.ui.screens.mainscreens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yogesh.stylish.domain.usecase.LogoutUseCase
import com.yogesh.stylish.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(

    val userEmail: String? = null, val didLogout: Boolean = false, val error: String? = null

)

@HiltViewModel
class ProfileViewModel @Inject constructor(

    private val firebaseAuth: FirebaseAuth, private val logoutUseCase: LogoutUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        val currentUser = firebaseAuth.currentUser
        _state.update { it.copy(userEmail = currentUser?.email) }
    }

    fun logout() {

        viewModelScope.launch { 
            
            when ( val result = logoutUseCase()){
               
                is Result.Success -> {
                    _state.update { it.copy(didLogout = true) }
                }
                
                is Result.Failure -> {
                    _state.update { it.copy(error = result.message) }
                }

                else -> {
                    
                }
            }
            
        }

    }

}