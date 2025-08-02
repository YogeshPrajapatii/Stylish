package com.yogesh.stylish.presentation.ui.screens.onboardingscreens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yogesh.stylish.domain.usecase.SaveOnboardingStatusUseCase

class OnboardingViewModelFactory(
    private val saveOnboardingStatusUseCase: SaveOnboardingStatusUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OnboardingViewModel(saveOnboardingStatusUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}