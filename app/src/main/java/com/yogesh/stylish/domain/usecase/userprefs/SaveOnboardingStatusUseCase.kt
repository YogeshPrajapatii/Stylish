package com.yogesh.stylish.domain.usecase.userprefs

import com.yogesh.stylish.domain.repository.userprefs.UserPreferenceRepository

class SaveOnboardingStatusUseCase(private val repository: UserPreferenceRepository) {

    suspend operator fun invoke() {
        repository.saveOnboardingStatus()
    }
}