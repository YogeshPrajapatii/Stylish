package com.yogesh.stylish.presentation.ui.screens.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yogesh.stylish.domain.repository.AuthRepository
import com.yogesh.stylish.domain.usecase.ReadOnboardingStatusUseCase

class SplashViewModelFactory(private val readOnboardingStatusUseCase: ReadOnboardingStatusUseCase,
                             private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return SplashViewModel(readOnboardingStatusUseCase,
                authRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")

    }

}