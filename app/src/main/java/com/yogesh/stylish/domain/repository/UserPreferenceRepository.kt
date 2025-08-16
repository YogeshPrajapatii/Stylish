package com.yogesh.stylish.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    fun readOnboardingStatus(): Flow<Boolean>
    suspend fun saveOnboardingStatus()
}