package com.yogesh.stylish.domain.repository.userprefs

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    fun readOnboardingStatus(): Flow<Boolean>
    suspend fun saveOnboardingStatus()
}