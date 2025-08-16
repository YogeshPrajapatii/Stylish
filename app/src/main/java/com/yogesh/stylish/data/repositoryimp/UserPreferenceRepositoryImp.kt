package com.yogesh.stylish.data.repositoryimp

import com.yogesh.stylish.data.local.UserPreferenceManager
import com.yogesh.stylish.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow

class UserPreferenceRepositoryImp(private val userPreferenceManager: UserPreferenceManager) :
    UserPreferenceRepository {
    override fun readOnboardingStatus(): Flow<Boolean> {
        return userPreferenceManager.getOnboardingStatus
    }

    override suspend fun saveOnboardingStatus() {
        userPreferenceManager.saveOnboardingStatus()
    }

}