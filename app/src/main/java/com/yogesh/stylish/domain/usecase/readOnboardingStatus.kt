package com.yogesh.stylish.domain.usecase

import com.yogesh.stylish.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow

class ReadOnboardingStatusUseCase(
    private val repository: UserPreferenceRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnboardingStatus()
    }
}