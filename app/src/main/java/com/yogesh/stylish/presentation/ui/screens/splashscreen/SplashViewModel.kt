package com.yogesh.stylish.presentation.ui.screens.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogesh.stylish.domain.repository.AuthRepository
import com.yogesh.stylish.domain.usecase.ReadOnboardingStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Represents the possible navigation destinations from the splash screen.
 * Using a sealed class ensures type safety.
 */
sealed class StartupDestination {
    object Onboarding : StartupDestination()
    object Home : StartupDestination()
    object Login : StartupDestination()
}

/**
 * ViewModel for the Splash screen.
 * Its primary job is to determine the initial navigation route for the user
 * based on their onboarding completion and authentication status.
 *
 * @param readOnboardingStatusUseCase UseCase to check if onboarding is completed.
 * @param authRepository Repository to check the current user's login status.
 */
class SplashViewModel(
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
            // 1. Check if the user has completed the onboarding process.
            // We use .first() to get only the first value emitted by the Flow.
            val isOnboardingCompleted = readOnboardingStatusUseCase().first()

            // 2. The Decision Tree logic.
            if (!isOnboardingCompleted) {
                // If onboarding is not complete, set the destination to Onboarding.
                _startupDestination.value = StartupDestination.Onboarding
            } else {
                // 3. If onboarding is complete, check the user's login status from Firebase Auth cache.
                if (authRepository.getCurrentUser() != null) {
                    // If user is logged in, set the destination to Home.
                    _startupDestination.value = StartupDestination.Home
                } else {
                    // If user is not logged in, set the destination to Login.
                    _startupDestination.value = StartupDestination.Login
                }
            }
        }
    }
}