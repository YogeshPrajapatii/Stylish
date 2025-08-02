package com.yogesh.stylish.presentation.ui.screens.onboardingscreens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.usecase.SaveOnboardingStatusUseCase
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val saveOnboardingStatusUseCase: SaveOnboardingStatusUseCase
) : ViewModel() {

    // Ye function hum UI se call karenge
    fun onOnboardingFinished() {
        viewModelScope.launch {
            saveOnboardingStatusUseCase()
        }
    }
}