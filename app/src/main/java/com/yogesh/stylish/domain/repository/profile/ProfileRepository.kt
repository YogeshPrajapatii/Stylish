package com.yogesh.stylish.domain.repository.profile

import com.yogesh.stylish.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<ProfileEntity?>
    suspend fun saveProfile(profile: ProfileEntity)
}