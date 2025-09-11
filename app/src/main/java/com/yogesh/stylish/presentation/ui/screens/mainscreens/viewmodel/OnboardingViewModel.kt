package com.yogesh.stylish.presentation.ui.screens.mainscreens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.usecase.SaveOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingStatusUseCase: SaveOnboardingStatusUseCase
) : ViewModel() {
    
    fun onOnboardingFinished() {
        viewModelScope.launch {
            saveOnboardingStatusUseCase()
        }
    }
}