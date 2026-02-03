package com.yogesh.stylish.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yogesh.stylish.data.local.entity.ProfileEntity
import com.yogesh.stylish.domain.usecase.auth.LogoutUseCase
import com.yogesh.stylish.domain.usecase.profile.GetProfileUseCase
import com.yogesh.stylish.domain.usecase.profile.UpdateProfileUseCase
import com.yogesh.stylish.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(
    val userEmail: String? = null,
    val didLogout: Result<String>? = null,
    val profileInfo: ProfileEntity? = null,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val logoutUseCase: LogoutUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        val currentUser = firebaseAuth.currentUser
        _state.update { it.copy(userEmail = currentUser?.email) }
        observeProfile()
    }

    private fun observeProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect { profile ->
                _state.update { it.copy(profileInfo = profile) }
            }
        }
    }

    fun saveProfile(
        imageUri: String?,
        fullName: String,
        email: String,
        pincode: String,
        address: String,
        city: String,
        bankAcc: String,
        holderName: String,
        ifsc: String
    ) {
        viewModelScope.launch {
            val updatedProfile = ProfileEntity(
                id = 1,
                imageUri = imageUri,
                fullName = fullName,
                email = email,
                pincode = pincode,
                address = address,
                city = city,
                bankAccountNumber = bankAcc,
                accountHolderName = holderName,
                ifscCode = ifsc
            )
            updateProfileUseCase(updatedProfile)
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase()
            _state.update { it.copy(didLogout = result) }
        }
    }
}