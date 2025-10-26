package com.yogesh.stylish.presentation.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.repository.auth.AuthRepository
import com.yogesh.stylish.domain.usecase.userprefs.ReadOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the possible navigation destinations from the splash screen.
 * Using a sealed class ensures type safety.
 */
sealed class StartupDestination {
    object Onboarding : StartupDestination()
    object Home : StartupDestination()
    object Login : StartupDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val readOnboardingStatusUseCase: ReadOnboardingStatusUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    // Private mutable state that holds the navigation decision. Only the ViewModel can change it.
    private val _startupDestination = MutableStateFlow<StartupDestination?>(null)

    // Public immutable state that the UI can observe to react to changes.
    val startupDestination: StateFlow<StartupDestination?> = _startupDestination

    /**
     * The init block is called when the ViewModel is first created.
     * The decision-making process starts immediately.
     */
    init {
        checkStartupDestination()
    }

    /**
     * Launches a coroutine to perform the startup checks in the background,
     * preventing the UI from freezing.
     */
    private fun checkStartupDestination() {
        viewModelScope.launch {
            val isOnboardingCompleted = readOnboardingStatusUseCase().first()

            val destination = if (!isOnboardingCompleted) {
                StartupDestination.Onboarding
            } else {
                if (authRepository.getCurrentUser() != null) {
                    StartupDestination.Home
                } else {
                    StartupDestination.Login
                }
            }

            
            delay(1000L) 

         
            _startupDestination.value = destination
        }
    }
}