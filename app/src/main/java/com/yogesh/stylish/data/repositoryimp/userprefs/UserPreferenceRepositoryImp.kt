package com.yogesh.stylish.data.repositoryimp.userprefs

import com.yogesh.stylish.data.local.data_store.user.UserPreferenceManager
import com.yogesh.stylish.domain.repository.userprefs.UserPreferenceRepository
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