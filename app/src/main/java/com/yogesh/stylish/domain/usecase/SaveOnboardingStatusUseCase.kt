package com.yogesh.stylish.domain.usecase

import com.yogesh.stylish.domain.repository.UserPreferenceRepository

class SaveOnboardingStatusUseCase(private val repository: UserPreferenceRepository) {

    suspend operator fun invoke() {
        repository.saveOnboardingStatus()
    }
}