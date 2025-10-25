package com.yogesh.stylish.domain.usecase.userprefs

import com.yogesh.stylish.domain.repository.userprefs.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow

class ReadOnboardingStatusUseCase(
    private val repository: UserPreferenceRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnboardingStatus()
    }
}